package com.cagecfi.productservice.api.impl;

import com.cagecfi.productservice.api.ICategoryRestController;
import com.cagecfi.productservice.dto.requests.CategoryRequest;
import com.cagecfi.productservice.dto.responses.CategoryResponse;
import com.cagecfi.productservice.service.ICategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Gestion des Categories")
public class ICategoryRestControllerImpl implements ICategoryRestController {

    private final ICategoryService service;

    @Override
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody @Valid CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
            @PathVariable String id,
            @RequestBody @Valid CategoryRequest request) {
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
    public ResponseEntity<CategoryResponse> get(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Override
    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponse>> search(@RequestParam(name = "sk") String searchKey) {
        return ResponseEntity.ok(service.search(searchKey));
    }
}
