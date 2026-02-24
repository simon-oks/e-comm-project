package com.cagecfi.cartservice.dtos.requests;

import java.util.List;

public record CartRequest(
        String customerId,
        List<CartLineRequest> lines
) {
}
