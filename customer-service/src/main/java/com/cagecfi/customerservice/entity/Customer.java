package com.cagecfi.customerservice.entity;

import com.cagecfi.customerservice.utils.IdGenerator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor
public class Customer {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    private Boolean deleted = false;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public Customer(String name, String email, String phone, String address){
        this.id = IdGenerator.generate();
        this.name = Objects.requireNonNull(name, "Le nom ne pas être null");
        this.email = Objects.requireNonNull(email, "L'email ne peut pas être null");
        this.phone = Objects.requireNonNull(phone, "Le phone est obligatoire ne peut pas être null");
        this.address = address;
    }

    public static Customer create(
            String name,
            String email,
            String phone,
            String address
    ){
        return new Customer(name, email, phone, address);
    }

    public void update(String name,
                       String email,
                       String phone,
                       String address){
        this.name = Objects.requireNonNull(name, "Le nom ne pas être null");
        this.email = Objects.requireNonNull(email, "L'email ne peut pas être null");
        this.phone = Objects.requireNonNull(phone, "Le phone est obligatoire ne peut pas être null");
        this.address = address;
    }

    public void delete() {
        this.deleted = true;
        this.deletedAt = Instant.now();
    }
}
