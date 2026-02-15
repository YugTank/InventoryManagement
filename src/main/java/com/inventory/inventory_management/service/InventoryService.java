package com.inventory.inventory_management.service;

import com.inventory.inventory_management.dto.request.StockUpdateRequest;
import com.inventory.inventory_management.dto.response.StockResponse;
import com.inventory.inventory_management.entity.InventoryLog;
import com.inventory.inventory_management.entity.InverntoryActions;
import com.inventory.inventory_management.entity.Product;
import com.inventory.inventory_management.repository.CategoryRepository;
import com.inventory.inventory_management.repository.InventoryLogRepository;
import com.inventory.inventory_management.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryLogRepository inventoryLogRepository;

    @Transactional
    public StockResponse updateStock(StockUpdateRequest  request) {
        log.info("Updating stock for product {}: {} {}",request.getProductId(),request.getOperation(),request.getQuantity());

        Product product=productRepository.findById(request.getProductId())
                .orElseThrow(()->new IllegalArgumentException("Product not found"));

        int quantityBefore=product.getQuantity();
        int quantityAfter;
        int quantityChanged;

        switch (request.getOperation()) {
            case ADD:
                quantityAfter=quantityBefore+request.getQuantity();
                quantityChanged=request.getQuantity();
                product.setQuantity(quantityAfter);
                break;

            case REMOVE:
                if(quantityBefore<request.getQuantity()){
                    throw new IllegalArgumentException("Insufficient quantity for product: "+product.getName()+" Available: "+quantityBefore+" Requested: "+request.getQuantity());
                }
                quantityAfter=quantityBefore-request.getQuantity();
                quantityChanged=request.getQuantity();
                product.setQuantity(quantityAfter);
                break;

            case ADJUST:
                quantityAfter= request.getQuantity();
                quantityChanged=quantityAfter-quantityBefore;
                product.setQuantity(quantityAfter);
                break;
            default:
                throw new IllegalArgumentException("Invalid operation"+request.getOperation());
        }

        productRepository.save(product);

        createLog(product, request.getOperation(), quantityChanged, quantityBefore, quantityAfter, request.getNotes());
        return new StockResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                quantityBefore,
                quantityAfter,
                quantityChanged,
                request.getOperation(),
                "Stock Updated Successfully"
        );
    }

    public List<InventoryLog> getProductLogs(Long productId){
        log.info("Getting inventory logs for product {}",productId);

        productRepository.findById(productId).orElseThrow(()->new IllegalArgumentException("Product not found"));

        return inventoryLogRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }

    public List<InventoryLog> getRecentLogs(){
        log.info("Getting recent logs for product");
        return inventoryLogRepository.findTop50ByOrderByCreatedAtDesc();
    }

    private void createLog(Product product, InverntoryActions actions, int quantityChange, int quantityBefore,
                           int quantityAfter, String notes) {
        InventoryLog inventoryLog = new InventoryLog();
        inventoryLog.setProduct(product);
        inventoryLog.setAction(actions);
        inventoryLog.setQuantityChange(quantityChange);
        inventoryLog.setQuantityBefore(quantityBefore);
        inventoryLog.setQuantityAfter(quantityAfter);
        inventoryLog.setNotes(notes);

        inventoryLogRepository.save(inventoryLog);
        log.info("Inventory Log Created Successfully for {}: {} {}",product.getName(),actions,quantityChange);
    }
}
