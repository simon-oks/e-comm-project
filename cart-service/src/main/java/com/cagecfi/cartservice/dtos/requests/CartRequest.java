package com.cagecfi.cartservice.dtos.requests;

import java.util.List;

public record CartRequest(
        String id,
        String userId,
        List<CartLineRequest> lines
) {
}
