package com.cagecfi.productservice.dto.responses;

import java.math.BigDecimal;

public record ProductResponse(
        String id,
        String name,
        String description,
        BigDecimal price,
        String fileName,
        CategoryResponse category,
        String createdAt,
        String updatedAt
) {
}
