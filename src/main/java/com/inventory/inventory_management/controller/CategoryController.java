package com.inventory.inventory_management.controller;

import com.inventory.inventory_management.entity.Category;
import com.inventory.inventory_management.service.CategoryService;
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
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        String name=category.getName();
        if(name==null || name.isEmpty()){
            throw new IllegalArgumentException("Category name is empty");
        }
        Category newCategory=categoryService.createCategory(name);
        return ResponseEntity.ok(newCategory);
    }
    @GetMapping
    public List<Category> getAllCategories(){
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id){
        return categoryService.findById(id);
    }
    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id,@RequestBody Category category){
        String name=category.getName();
        if(name==null || name.isEmpty()){
            throw new IllegalArgumentException("Category name is empty");
        }
        return categoryService.updateCategory(id, name);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}
