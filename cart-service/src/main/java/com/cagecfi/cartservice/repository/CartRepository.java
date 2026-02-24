package com.cagecfi.cartservice.repository;

import com.cagecfi.cartservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByCustomerId(String customerId);
}
