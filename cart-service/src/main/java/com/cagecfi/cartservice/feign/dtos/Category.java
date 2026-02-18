package com.cagecfi.cartservice.feign.dtos;

public record CategoryResponse(
        String id,
        String libelle,
        String createdAt,
        String updatedAt
) {
}
