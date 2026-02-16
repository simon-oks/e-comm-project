package com.cagecfi.productservice.mapper;

import com.cagecfi.productservice.dto.requests.CategoryRequest;
import com.cagecfi.productservice.dto.responses.CategoryResponse;
import com.cagecfi.productservice.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public Category toEntity(CategoryRequest request) {
        if (request == null) return null;
        return Category.create(request.libelle());
    }

    public void update(Category category, CategoryRequest request){
        if (request == null) return;
        category.update(request.libelle());
    }

    public CategoryResponse toResponse(Category c) {
        if (c == null) return null;
        return new CategoryResponse(
                c.getId(),
                c.getLibelle(),
                c.getCreatedAt() != null ? c.getCreatedAt().toString() : null,
                c.getUpdatedAt() != null ? c.getUpdatedAt().toString() : null
        );
    }
}
