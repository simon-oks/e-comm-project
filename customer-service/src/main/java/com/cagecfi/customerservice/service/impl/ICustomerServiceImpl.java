package com.cagecfi.customerservice.service.impl;

import com.cagecfi.customerservice.dto.CustomerRequest;
import com.cagecfi.customerservice.dto.CustomerResponse;
import com.cagecfi.customerservice.entity.Customer;
import com.cagecfi.customerservice.exceptions.CustomerNotFoundException;
import com.cagecfi.customerservice.mapper.CustomerMapper;
import com.cagecfi.customerservice.repository.CustomerRepository;
import com.cagecfi.customerservice.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ICustomerServiceImpl implements ICustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {
        log.info("Creating customer: Name = {}", request.name());
        Customer customer = mapper.toEntity(request);
        customer = repository.save(customer);
        return mapper.toResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(String id, CustomerRequest request) {
        log.info("Updating customer: Id = {}", id);
        Customer customer = findCustomerById(id);
        mapper.updateCustomer(customer, request);
        customer = repository.save(customer);
        return mapper.toResponse(customer);
    }

    @Override
    public void deleteCustomer(String id) {
        log.info("Deleting customer: Id = {}", id);
        Customer customer = findCustomerById(id);
        customer.delete();
        repository.save(customer);
    }

    @Override
    public CustomerResponse getCustomer(String id) {
        log.info("Getting customer: Id = {}", id);
        return mapper.toResponse(findCustomerById(id));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        log.info("Getting all no deleted customers");
        return repository.findAllByDeletedIsFalse()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<CustomerResponse> searchCustomers(String searchKey) {
        return repository.searchByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchKey, searchKey)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public Customer findCustomerById(String id) {
        return repository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }


}
