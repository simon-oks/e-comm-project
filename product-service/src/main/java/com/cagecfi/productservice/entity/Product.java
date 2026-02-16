package com.cagecfi.productservice.entity;

import com.cagecfi.productservice.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;

    @ManyToOne(optional = false)
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public Product(String name, String description, BigDecimal price, Category category){
        this.id = IdGenerator.generate();
        this.name = Objects.requireNonNull(name, "Le nom du produit ne peut pas etre null");
        this.description = description;
        this.price = Objects.requireNonNull(price, "Le prix du produit ne peut pas etre null");
        this.category = Objects.requireNonNull(category, "La catégorie du produit ne peut pas etre null");
    }

    public static Product create(String name, String description, BigDecimal price, Category category){
        return new Product(name, description, price, category);
    }

    public void update(String name, String description, BigDecimal price, Category category){
        this.name = Objects.requireNonNull(name, "Le nom du produit ne peut pas etre null");
        this.description = description;
        this.price = Objects.requireNonNull(price, "Le prix du produit ne peut pas etre null");
        this.category = Objects.requireNonNull(category, "La catégorie du produit ne peut pas etre null");
    }

    public void delete() {
        this.deleted = true;
        this.deletedAt = Instant.now();
    }
}
