package com.cagecfi.orderservice.dto.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record OrderRequest(
        @NotBlank(message = "customerId can't be null")
        String customerId,
        List<@Valid OrderLineRequest> lines
) { }
