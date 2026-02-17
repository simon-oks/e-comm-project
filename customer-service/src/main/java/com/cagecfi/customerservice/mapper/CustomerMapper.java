package com.cagecfi.customerservice.mapper;

import com.cagecfi.customerservice.dto.CustomerRequest;
import com.cagecfi.customerservice.dto.CustomerResponse;
import com.cagecfi.customerservice.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public Customer toEntity(CustomerRequest request){
        if (request == null) return null;
        return Customer.create(request.name(), request.email(), request.phone(), request.address());
    }

    public void updateCustomer(Customer customer, CustomerRequest request){
        if (request == null) return;
        customer.update(request.name(), request.email(), request.phone(), request.address());
    }

    public CustomerResponse toResponse(Customer customer){
        if(customer == null) return null;
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhone(),
                customer.getAddress(),
                customer.getCreatedAt() != null ? customer.getCreatedAt().toString() : null,
                customer.getUpdatedAt() != null ? customer.getUpdatedAt().toString() : null
        );
    }
}
