package com.cagecfi.cartservice.feign.dtos;

import java.math.BigDecimal;

public record Product(
        String id,
        String name,
        String description,
        BigDecimal price,
        Category category,
        String createdAt,
        String updatedAt
) {
}
