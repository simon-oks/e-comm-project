package com.cagecfi.cartservice.dtos.responses;

import java.math.BigDecimal;

public record CartLineResponse(
        String id,
        String productId,
        BigDecimal unitPrice,
        BigDecimal quantity,
        BigDecimal lineTotal,
        String createdAt,
        String updatedAt
) {

}
