package com.inventory.inventory_management.controller;

import com.inventory.inventory_management.dto.request.CategoryRequest;
import com.inventory.inventory_management.dto.response.CategoryResponse;
import com.inventory.inventory_management.entity.Category;
import com.inventory.inventory_management.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        Category newCategory=categoryService.createCategory(categoryRequest);
        return ResponseEntity.ok(newCategory);
    }
    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        return categoryService.findAll()
                .stream()
                .map(CategoryResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public CategoryResponse getCategoryById(@PathVariable Long id){
        Category category=categoryService.findById(id);
        return CategoryResponse.fromEntity(category);
    }
    @PutMapping("/{id}")
    public CategoryResponse updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest){
        Category updatedCategory=categoryService.updateCategory(id,categoryRequest);
        return CategoryResponse.fromEntity(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}
