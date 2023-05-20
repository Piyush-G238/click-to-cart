package com.piyush.inventoryservice.repository;

import com.piyush.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    boolean existsByProductId(Long productId);

    Optional<Inventory> findByProductId(Long productId);
}
