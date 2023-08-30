package com.tads.bantads.expection.dto;

import org.springframework.validation.FieldError;

public record CustomerInvalidDTO(String field, String message){
    public CustomerInvalidDTO(FieldError error){
        this(error.getField(), error.getDefaultMessage());
    }
}
