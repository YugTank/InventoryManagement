package com.inventory.inventory_management.controller;

import com.inventory.inventory_management.dto.request.StockUpdateRequest;
import com.inventory.inventory_management.dto.response.InventoryLogResponse;
import com.inventory.inventory_management.dto.response.StockResponse;
import com.inventory.inventory_management.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public ResponseEntity<StockResponse> updateStock(@Valid @RequestBody StockUpdateRequest request){
        StockResponse stockResponse = inventoryService.updateStock(request);
        return ResponseEntity.ok(stockResponse);
    }

    @GetMapping("/logs/product/{productId}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public List<InventoryLogResponse> getProductLogs(@PathVariable Long productId){
        return inventoryService.getProductLogs(productId)
                .stream()
                .map(InventoryLogResponse::fromEntity)
                .toList();
    }

    @GetMapping("/logs/recent")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    public List<InventoryLogResponse> getRecentLogs(){
        return inventoryService.getRecentLogs()
                .stream()
                .map(InventoryLogResponse::fromEntity)
                .toList();
    }
}
