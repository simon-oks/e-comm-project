package com.cagecfi.productservice.service;

import com.cagecfi.productservice.dto.requests.CategoryRequest;
import com.cagecfi.productservice.dto.responses.CategoryResponse;
import com.cagecfi.productservice.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    CategoryResponse create(CategoryRequest request);
    CategoryResponse update(String id, CategoryRequest request);
    void delete(String id);
    CategoryResponse get(String id);
    List<CategoryResponse> getAll();
    List<CategoryResponse> search(String searchKey);
    Optional<Category> getByLibelle(String name);
    Category findByIdAndDeletedIsFalse(String id);
}