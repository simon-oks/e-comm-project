package com.cagecfi.productservice.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        @NotBlank(message = "Le libelle de la catégorie ne peut pas etre null")
        String libelle
) {
}
