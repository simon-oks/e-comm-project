package com.cagecfi.productservice.api.impl;

import com.cagecfi.productservice.api.IProductRestController;
import com.cagecfi.productservice.dto.requests.ProductRequest;
import com.cagecfi.productservice.dto.responses.ProductResponse;
import com.cagecfi.productservice.service.IProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Gestion des Produits")
public class IProductRestControllerImpl implements IProductRestController {

    private final IProductService service;

    @Override
    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable String id, @RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getByCategory(@PathVariable String categoryId) {
        return ResponseEntity.ok(service.getByCategory(categoryId));
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> search(@RequestParam(name = "sk") String searchKey) {
        return ResponseEntity.ok(service.search(searchKey));
    }
}
