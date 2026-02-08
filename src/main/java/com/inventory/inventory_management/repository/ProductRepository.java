package com.inventory.inventory_management.repository;

import com.inventory.inventory_management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsBySku(String sku);

    Optional<Product> findBySku(String sku);

    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategory(String category);

    @Query("SELECT p FROM Product p WHERE p.quantity<=p.lowStockThreshold")
    List<Product> findLowStockProducts();

    List<Product> findByCategoryId(Long categoryId);
}
