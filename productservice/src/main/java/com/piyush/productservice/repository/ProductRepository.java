package com.piyush.productservice.repository;

import com.piyush.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Transactional
    @Modifying
    @Query("delete from Product p where p.name = ?1")
    void deleteByName(String name);
    boolean existsByName(String name);

    @Query(value = "from Product p where p.name =?1")
    Optional<Product> getProductByName(String name);
}
