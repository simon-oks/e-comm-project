package com.cagecfi.customerservice.repository;

import com.cagecfi.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByIdAndDeletedIsFalse(String id);
    Optional<Customer> findByEmailAndPasswordAndDeletedFalse(String email, String password);
    List<Customer> searchByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);
    List<Customer> findAllByDeletedIsFalse();
}
