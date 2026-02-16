package com.cagecfi.productservice.mapper;

import com.cagecfi.productservice.dto.requests.ProductRequest;
import com.cagecfi.productservice.dto.responses.ProductResponse;
import com.cagecfi.productservice.entity.Category;
import com.cagecfi.productservice.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryMapper categoryMapper;

    public Product toEntity(ProductRequest request, Category category){
        if (request == null) return null;
        return Product.create(
                request.name(),
                request.description(),
                request.price(),
                category
        );
    }

    public void update(ProductRequest request, Product product, Category category){
        if (request == null) return;
        product.update(
                request.name(),
                request.description(),
                request.price(),
                category
        );
    }

    public ProductResponse toResponse(Product p){
        if (p == null) return null;
        return new ProductResponse(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getPrice(),
                categoryMapper.toResponse(p.getCategory()),
                p.getCreatedAt() != null ? p.getCreatedAt().toString() : null,
                p.getUpdatedAt() != null ? p.getUpdatedAt().toString() : null
        );
    }
}
