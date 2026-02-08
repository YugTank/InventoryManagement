package com.inventory.inventory_management.dto.response;

import com.inventory.inventory_management.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Long id;
    private String name;
    private LocalDateTime creationDate;

    public static CategoryResponse fromEntity(Category category){
        return new CategoryResponse(category.getId(), category.getName(),category.getCreatedAt());
    }
}
