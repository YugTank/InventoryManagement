package com.inventory.inventory_management.service;

import com.inventory.inventory_management.entity.Category;
import com.inventory.inventory_management.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(String name) {
        log.info("Creating category {}", name);
        if(categoryRepository.existsByName(name)){
            throw new IllegalArgumentException("Category with name " + name + " already exists");
        }
        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    public List<Category> findAll() { return categoryRepository.findAll(); }
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + id + " does not exist"));
    }

    @Transactional
    public Category updateCategory(Long id, String newName) {
        log.info("Updating category {}", id);
        Category category = findById(id);

        Category exisitng=categoryRepository.findByName(newName);
        if(exisitng!=null){
            throw  new IllegalArgumentException("Category with name " + newName + " already exists");
        }
        category.setName(newName);
        return categoryRepository.save(category);
    }

    @Transactional
    public void deleteCategory(Long id) {
        if(categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
        }
        else{
            throw new IllegalArgumentException("Category with id " + id + " does not exist");
        }
    }
}
