package com.cagecfi.cartservice.dtos.responses;

import java.util.List;

public record CartResponse(
        String id,
        String customerId,
        List<CartLineResponse> lines,
        String createdAt,
        String updatedAt
) {
}
