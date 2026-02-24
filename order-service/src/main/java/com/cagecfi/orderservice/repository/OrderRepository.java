package com.cagecfi.orderservice.repository;

import com.cagecfi.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findByCustomerId(String customerId);
    List<Order> findAllByCustomerId(String customerId);
}
