package com.cagecfi.cartservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "cart_lines")
@Getter
@NoArgsConstructor
public class CartLine {
    @Id
    private String id;

    @ManyToOne(optional = false)
    private Cart cart;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "line_total", nullable = false)
    private BigDecimal lineTotal;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
