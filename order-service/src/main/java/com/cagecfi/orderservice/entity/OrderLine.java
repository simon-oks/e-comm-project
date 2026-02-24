package com.cagecfi.orderservice.entity;

import com.cagecfi.orderservice.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_lines")
@Getter
@NoArgsConstructor
public class OrderLine {
    @Id
    private String id;

    @ManyToOne
    private Order order;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "line_total_price", nullable = false)
    private BigDecimal lineTotalPrice;

    public OrderLine(Order order, String productId, BigDecimal quantity, BigDecimal unitPrice){
        this.id = IdGenerator.generate();
        this.order = Objects.requireNonNull(order, "La commande ne peux pas être null");
        this.productId = Objects.requireNonNull(productId, "L'ID du produit ne peut pas être null");
        this.quantity = Objects.requireNonNull(quantity, "La quantité ne peut pas être null");
        this.unitPrice = Objects.requireNonNull(unitPrice, "Le prix unitaire ne peux pas être null");
        this.lineTotalPrice = unitPrice.multiply(quantity);
    }

    public static OrderLine create(Order order, String productId, BigDecimal quantity, BigDecimal unitPrice){
        return new OrderLine(order, productId, quantity, unitPrice);
    }
}
