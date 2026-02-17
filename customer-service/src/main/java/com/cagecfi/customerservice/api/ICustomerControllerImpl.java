package com.cagecfi.customerservice.api;

import com.cagecfi.customerservice.dto.CustomerRequest;
import com.cagecfi.customerservice.dto.CustomerResponse;
import com.cagecfi.customerservice.service.ICustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Tag(name = "Gestion des clients")
public class ICustomerControllerImpl implements ICustomerController {

    private final ICustomerService service;

    @Override
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCustomer(request));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable String id,
            @RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(service.updateCustomer(id, request));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String id) {
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable String id) {
        return ResponseEntity.ok(service.getCustomer(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(service.getAllCustomers());
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<List<CustomerResponse>> searchCustomers(@RequestParam(name = "sk") String searchKey) {
        return ResponseEntity.ok(service.searchCustomers(searchKey));
    }
}
