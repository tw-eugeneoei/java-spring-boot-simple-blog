/* (C)2022 */
package com.example.simpleblog.dto;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDto {
    @NotEmpty(message = "Field cannot be empty.")
    private String emailOrUsername;

    @NotEmpty(message = "Field cannot be empty.")
    private String password;
}
