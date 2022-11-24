package com.example.simpleblog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginDto {
    @NotEmpty(message = "Field cannot be empty.")
    private String emailOrUsername;

    @NotEmpty(message = "Field cannot be empty.")
    private String password;
}
