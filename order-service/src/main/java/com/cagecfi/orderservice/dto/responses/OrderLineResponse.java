package com.cagecfi.orderservice.dto.responses;

import java.math.BigDecimal;

public record OrderLineResponse(
        String id,
        String productId,
        BigDecimal quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotalPrice
) {}
