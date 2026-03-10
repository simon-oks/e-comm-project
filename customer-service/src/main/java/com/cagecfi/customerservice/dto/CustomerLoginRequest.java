package com.cagecfi.customerservice.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerLoginRequest(
        @NotBlank(message = "Email can't be blank")
        String email,

        @NotBlank(message = "Password can't be blank")
        String password
) {
}
