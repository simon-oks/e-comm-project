package com.cagecfi.orderservice.dto.responses;

import java.util.List;

public record OrderResponse(
        String id,
        String customerId,
        List<OrderLineResponse> lines,
        String createdAt,
        String updatedAt
) {}
