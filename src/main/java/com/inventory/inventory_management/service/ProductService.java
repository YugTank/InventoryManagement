package com.inventory.inventory_management.service;

import com.inventory.inventory_management.dto.request.ProductRequest;
import com.inventory.inventory_management.dto.request.ProductUpdateRequst;
import com.inventory.inventory_management.entity.Category;
import com.inventory.inventory_management.entity.Product;
import com.inventory.inventory_management.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    @Transactional
    public Product createProduct(ProductRequest request){
        log.info("Creating product "+request.getName());
        if(productRepository.existsBySku(request.getSku())){
            throw new IllegalArgumentException("Product with given SKU already exists");
        }

        Category category=categoryService.findById(request.getCategoryId());

        Product product=new Product();
        product.setName(request.getName());
        product.setSku(request.getSku());
        product.setCategory(category);
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity()!=null?request.getQuantity():0);
        product.setLowStockThreshold(request.getLowStockThreshold()!=null?request.getLowStockThreshold():10);

        return productRepository.save(product);
    }
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Product getProductById(Long id){
        log.info("Getting product by id "+id);
        return productRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Product with id "+id+" not found"));
    }

    public Product getProductBySku(String sku){
        log.info("Getting product by sku "+sku);
        return productRepository.findBySku(sku)
                .orElseThrow(()->new IllegalArgumentException("Product with id "+sku+" not found"));
    }

    public List<Product> searchProduct(String name){
        log.info("Searching product by name "+name);
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product>  searchProductByCategory(Long categoryId){
        log.info("Searching product by category "+categoryId);
        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getLowStockProducts(){
        log.info("Getting low stock products");
        return productRepository.findLowStockProducts();
    }
    @Transactional
    public Product updateProduct(Long id, ProductUpdateRequst request){
        log.info("Updating product "+request.getName());
        Product product=productRepository.findById(id).orElseThrow(()->new IllegalArgumentException("Product with id "+id+" not found"));

        if(request.getName()!=null){
            product.setName(request.getName());
        }
        if(request.getDescription()!=null){
            product.setDescription(request.getDescription());
        }
        if(request.getPrice()!=null){
            product.setPrice(request.getPrice());
        }
        if(request.getLowStockThreshold()!=null){
            product.setLowStockThreshold(request.getLowStockThreshold());
        }
        if(request.getCategoryId()!=null){
            Category category=categoryService.findById(request.getCategoryId());
            product.setCategory(category);
        }
        log.info("Updated name: {}", product.getName());
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProductById(Long id){
        log.info("Deleting product "+id);
        productRepository.deleteById(id);
    }
}
