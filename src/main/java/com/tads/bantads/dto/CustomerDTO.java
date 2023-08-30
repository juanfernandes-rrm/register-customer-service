package com.tads.bantads.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

public record CustomerDTO (
        UUID id,
        @NotBlank
        String name,
        @NotBlank @Email
        String email,
        @NotBlank @CPF
        String cpf,
        @NotNull @Valid
        AddressDTO address){}