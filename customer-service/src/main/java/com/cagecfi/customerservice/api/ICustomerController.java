package com.cagecfi.customerservice.api;

import com.cagecfi.customerservice.dto.CustomerRequest;
import com.cagecfi.customerservice.dto.CustomerResponse;
import com.cagecfi.customerservice.entity.Customer;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICustomerController {
    ResponseEntity<CustomerResponse> createCustomer(@Valid CustomerRequest request);
    ResponseEntity<CustomerResponse> updateCustomer(String id, @Valid CustomerRequest request);
    ResponseEntity<Void> deleteCustomer(String id);
    ResponseEntity<CustomerResponse> getCustomer(String id);
    ResponseEntity<List<CustomerResponse>> getAllCustomers();
    ResponseEntity<List<CustomerResponse>> searchCustomers(String searchKey);
}
