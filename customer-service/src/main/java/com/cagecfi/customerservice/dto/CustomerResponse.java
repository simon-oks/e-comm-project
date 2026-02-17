package com.cagecfi.customerservice.dto;

public record CustomerResponse(
        String id,
        String name,
        String email,
        String phone,
        String address,
        String createdAt,
        String updatedAt
) {
}
