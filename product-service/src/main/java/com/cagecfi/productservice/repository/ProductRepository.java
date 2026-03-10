package com.cagecfi.productservice.repository;

import com.cagecfi.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findByIdAndDeletedIsFalse(String id);
    List<Product> findAllByCategory_IdAndDeletedIsFalse(String category_id);
    List<Product> findAllByDeletedIsFalse();
    List<Product> searchByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndDeletedIsFalse(String name, String description);

    boolean existsByName(String productName);
}
