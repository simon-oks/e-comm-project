package com.cagecfi.productservice.api;

import com.cagecfi.productservice.dto.requests.ProductRequest;
import com.cagecfi.productservice.dto.responses.ProductResponse;
import com.cagecfi.productservice.entity.Product;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductRestController {
    ResponseEntity<ProductResponse> create(@Valid ProductRequest request);
    ResponseEntity<ProductResponse> update(String id, @Valid ProductRequest request);
    ResponseEntity<Void> delete(String id);
    ResponseEntity<ProductResponse> get(String id);
    ResponseEntity<List<ProductResponse>> getAll();
    ResponseEntity<List<ProductResponse>> getByCategory(String categoryId);
    ResponseEntity<List<ProductResponse>> search(String searchKey);
}
