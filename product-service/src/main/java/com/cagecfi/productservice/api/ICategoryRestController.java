package com.cagecfi.productservice.api;

import com.cagecfi.productservice.dto.requests.CategoryRequest;
import com.cagecfi.productservice.dto.responses.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICategoryRestController {
    ResponseEntity<CategoryResponse> create(@Valid CategoryRequest request);
    ResponseEntity<CategoryResponse> update(String id, @Valid CategoryRequest request);
    ResponseEntity<Void> delete(String id);
    ResponseEntity<CategoryResponse> get(String id);
    ResponseEntity<List<CategoryResponse>> getAll();
    ResponseEntity<List<CategoryResponse>> search(String searchKey);
}
