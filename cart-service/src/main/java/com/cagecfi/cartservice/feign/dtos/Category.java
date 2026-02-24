package com.cagecfi.cartservice.feign.dtos;

public record Category(
        String id,
        String libelle,
        String createdAt,
        String updatedAt
) {
}
