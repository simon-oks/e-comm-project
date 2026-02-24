package com.cagecfi.orderservice.entity;

import com.cagecfi.orderservice.utils.IdGenerator;
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
@Table(name = "order_lines")
@Getter
@NoArgsConstructor
public class Order {
    @Id
    private String id;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "status", nullable = false)
    private OderLine status;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> lines;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Order(String customerId){
        this.id = IdGenerator.generate();
        this.customerId = Objects.requireNonNull(customerId, "L'ID du client ne peut pas être null");
        this.status = OderLine.CREATED;
        this.lines = new ArrayList<>();
    }

    public static Order create(String customerId){
        return new Order(customerId);
    }

    public void addOrderLine(OrderLine line){
        this.lines.add(line);
    }
}
