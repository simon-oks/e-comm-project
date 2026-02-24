package com.cagecfi.cartservice.entity;

import com.cagecfi.cartservice.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

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

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "line_total", nullable = false)
    private BigDecimal lineTotal;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public CartLine(Cart cart, String productId, BigDecimal quantity, BigDecimal unitPrice){
        this.id = IdGenerator.generate();
        this.cart = Objects.requireNonNull(cart, "La cart ne peut pas être null");
        this.productId = Objects.requireNonNull(productId, "L'ID du produit ne peut pas être null");
        this.quantity = Objects.requireNonNull(quantity, "La quantité ne peut pas être null");
        this.unitPrice = Objects.requireNonNull(unitPrice, "Le prix unitaire ne peux pas être null");
        this.lineTotal = unitPrice.multiply(quantity);
    }

    public static CartLine create(Cart cart, String productId, BigDecimal quantity, BigDecimal unitPrice){
        return new CartLine(cart, productId, quantity, unitPrice);
    }

    public void update(BigDecimal quantity){
        this.quantity = quantity;
        this.lineTotal = this.unitPrice.multiply(this.quantity);
    }
}
