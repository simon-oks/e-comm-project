package com.cagecfi.cartservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "carts")
@Getter
@NoArgsConstructor
public class Cart {
    @Id
    private String id;

    @Column(unique = true)
    private String userId;

    @OneToMany(mappedBy = "cart")
    private List<CartLine> lines;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
