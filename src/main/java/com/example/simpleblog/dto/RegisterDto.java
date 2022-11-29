package com.example.simpleblog.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegisterDto {
    @NotEmpty(message = "Field cannot be empty.")
    private String username;

    @NotEmpty(message = "Field cannot be empty.")
    @Email(message = "Email provided is invalid.")
    private String email;

    @NotEmpty(message = "Field cannot be empty.")
    private String firstName;

    @NotEmpty(message = "Field cannot be empty.")
    private String lastName;

    @NotEmpty(message = "Field cannot be empty.")
    @Size(min = 12, message = "Password must be at least 12 characters.")
    private String password;

    @NotEmpty(message = "Field cannot be empty.")
    @Size(min = 12, message = "Confirm Password must be at least 12 characters.")
    private String confirmPassword;
}
