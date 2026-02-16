package com.cagecfi.productservice.entity;

import com.cagecfi.productservice.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor
public class Category {
    @Id
    private String id;

    @Column(unique = true)
    private String libelle;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public Category(String libelle) {
        this.id = IdGenerator.generate();
        this.libelle = Objects.requireNonNull(libelle, "Le libelle de la catégorie ne peut pas etre null");
        this.products = new ArrayList<>();
    }

    public static Category create(String libelle) {
        return new Category(libelle);
    }

    public void update(String libelle) {
        this.libelle = Objects.requireNonNull(libelle, "Le libelle de la catégorie ne peut pas etre null");
    }

    public void delete() {
        this.deleted = true;
        this.deletedAt = Instant.now();
    }

    public void unDelete() {
        this.deleted = false;
        this.deletedAt = null;
    }
}
