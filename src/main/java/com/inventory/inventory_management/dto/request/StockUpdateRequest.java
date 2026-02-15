package com.inventory.inventory_management.dto.request;

import com.inventory.inventory_management.entity.InverntoryActions;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockUpdateRequest {

    @NotNull(message = "ProductID is required")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Operation is required")
    private InverntoryActions operation;

    private String notes;
}
