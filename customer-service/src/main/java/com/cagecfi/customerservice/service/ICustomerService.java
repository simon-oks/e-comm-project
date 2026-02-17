package com.cagecfi.customerservice.service;

import com.cagecfi.customerservice.dto.CustomerRequest;
import com.cagecfi.customerservice.dto.CustomerResponse;
import com.cagecfi.customerservice.entity.Customer;

import java.util.List;

public interface ICustomerService {
    CustomerResponse createCustomer(CustomerRequest request);
    CustomerResponse updateCustomer(String id, CustomerRequest request);
    void deleteCustomer(String id);
    CustomerResponse getCustomer(String id);
    List<CustomerResponse> getAllCustomers();
    List<CustomerResponse> searchCustomers(String searchKey);

    Customer findCustomerById(String id);
}
