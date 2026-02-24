package com.cagecfi.cartservice.feign.dtos;

public record Customer(
        String id,
        String name,
        String email,
        String phone,
        String address,
        String createdAt,
        String updatedAt
) {
}
