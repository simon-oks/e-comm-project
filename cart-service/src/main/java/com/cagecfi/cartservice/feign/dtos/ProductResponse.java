package com.cagecfi.cartservice.feign.dtos;

import java.math.BigDecimal;

public record ProductResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        CategoryResponse category,
        String createdAt,
        String updatedAt
) {
}
