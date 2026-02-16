package com.cagecfi.productservice.dto.responses;

public record CategoryResponse(
        String id,
        String libelle,
        String createdAt,
        String updatedAt
) {
}
