package com.example.simpleblog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserPasswordDto {
    @NotEmpty(message = "Old password cannot be empty.")
    private String oldPassword;

    @NotEmpty(message = "New password cannot be empty.")
    @Size(min = 12, message = "Password must be at least 12 characters.")
    private String newPassword;

    @NotEmpty(message = "Confirm new password cannot be empty.")
    @Size(min = 12, message = "Password must be at least 12 characters.")
    private String confirmNewPassword;
}
