package com.inventory.inventory_management.dto.response;

import com.inventory.inventory_management.entity.InverntoryActions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockResponse {

    private Long productId;
    private String sku;
    private String productName;
    private Integer quantityBefore;
    private Integer quantityAfter;
    private Integer quantityChanged;
    private InverntoryActions operation;
    private String message;
}
