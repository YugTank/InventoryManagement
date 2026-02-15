package com.inventory.inventory_management.repository;

import com.inventory.inventory_management.entity.InventoryLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryLogRepository extends JpaRepository<InventoryLog,Long> {

    List<InventoryLog> findByProductIdOrderByCreatedAtDesc(Long productId);

    @Query(value = "select i from InventoryLog i order by createdAt limit 50")
    List<InventoryLog> findTop50ByOrderByCreatedAtDesc();
}
