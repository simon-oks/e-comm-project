package com.cagecfi.cartservice.dtos.requests;

public record CartLineRequest(
        String productId,
        double quantity
) {
}
