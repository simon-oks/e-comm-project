package com.cagecfi.productservice.repository;

import com.cagecfi.productservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByIdAndDeletedIsFalse(String id);
    List<Category> findByLibelleContainingIgnoreCase(String s);
    List<Category> findAllByDeletedIsFalse();
    List<Category> searchByLibelleContainingIgnoreCaseAndDeletedIsFalse(String libelle);
    Optional<Category> findByLibelle(String libelle);
}
