package com.cagecfi.productservice.dto.requests;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        String description,
        BigDecimal price,
        String categoryId
) {
}
