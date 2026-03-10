package com.cagecfi.productservice.dto.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank(message = "")
        String name,
        String description,
        @Min(value = 2, message = "price can't be less than 2")
        BigDecimal price,
        String fileName,
        String categoryId
) {
}
