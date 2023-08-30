package com.tads.bantads.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressDTO(
        @Pattern(regexp = "\\d{5}-\\d{3}")
        @NotBlank
        String cep,
        @NotBlank
        String street,
        @NotBlank
        String number,
        @NotBlank
        String neighborhood,
        @NotBlank
        String city,
        @NotBlank
        String state,
        String complement) {
}
