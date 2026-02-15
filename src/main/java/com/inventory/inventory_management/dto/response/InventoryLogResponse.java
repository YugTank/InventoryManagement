package com.inventory.inventory_management.dto.response;

import com.inventory.inventory_management.entity.InventoryLog;
import com.inventory.inventory_management.entity.InverntoryActions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryLogResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String sku;
    private InverntoryActions action;
    private Integer quantityChange;
    private Integer quantityBefore;
    private Integer quantityAfter;
    private String notes;
    private LocalDateTime createdDate;

    public static InventoryLogResponse fromEntity(InventoryLog log){
        return new InventoryLogResponse(
                log.getId(),
                log.getProduct().getId(),
                log.getProduct().getName(),
                log.getProduct().getSku(),
                log.getAction(),
                log.getQuantityChange(),
                log.getQuantityBefore(),
                log.getQuantityAfter(),
                log.getNotes(),
                log.getCreatedAt()
        );
    }
}
