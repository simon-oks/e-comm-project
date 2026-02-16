package com.cagecfi.cartservice.dtos.requests;

import java.math.BigDecimal;

public record CartLineRequest(
        String productId,
        BigDecimal unitPrice,
        BigDecimal quantity,
        BigDecimal lineTotal
) {
}
