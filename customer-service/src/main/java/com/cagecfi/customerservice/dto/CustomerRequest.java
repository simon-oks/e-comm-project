package com.cagecfi.customerservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(
        @NotBlank(message = "Name can't be blank")
        String name,

        @NotBlank(message = "Email can't be blank")
        @Email(message = "Email is not valid")
        String email,

        @NotBlank(message = "Phone can't be blank")
        String phone,
        String address
) {
}
