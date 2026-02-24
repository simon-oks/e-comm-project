package com.cagecfi.cartservice.entity;

import com.cagecfi.cartservice.utils.IdGenerator;
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
@Table(name = "carts")
@Getter
@NoArgsConstructor
public class Cart {
    @Id
    private String id;

    @Column(unique = true, nullable = false)
    private String customerId;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartLine> lines;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public Cart(String customerId){
        this.id = IdGenerator.generate();
        this.customerId = Objects.requireNonNull(customerId, "L'ID du client ne peut pas être null");
        this.lines = new ArrayList<>();
    }

    public static Cart create(String customerId){
        return new Cart(customerId);
    }

    public void addCartLine(CartLine line){
        this.lines.add(line);
    }

    public void deleteCartLine(CartLine line){
        this.lines.remove(line);
    }
}
