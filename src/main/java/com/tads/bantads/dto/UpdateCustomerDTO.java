package com.tads.bantads.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCustomerDTO(
                                @NotBlank
                                String name,
                                @NotBlank @Email
                                String email,
                                @NotNull @Valid
                                AddressDTO address) {
}
