package com.inventory.inventory_management.dto.response;

import com.inventory.inventory_management.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Integer lowStockThreshold;
    private boolean lowStock;
    private CategoryResponse category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getLowStockThreshold(),
                product.isLowStock(),
                CategoryResponse.fromEntity(product.getCategory()),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }

}
