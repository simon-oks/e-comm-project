package com.cagecfi.orderservice.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderLineRequest(
        @NotBlank(message = "productId can't be blank")
        String productId,

        @NotNull(message = "quantity can't be null")
        BigDecimal quantity,

        @NotNull(message = "unitPrice can't be null")
        BigDecimal unitPrice,

        @NotNull(message = "lineTotalPrice can't be null")
        BigDecimal lineTotalPrice
) {

}
