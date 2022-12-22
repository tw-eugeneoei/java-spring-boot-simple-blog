/* (C)2022 */
package com.example.simpleblog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.UUID;
import lombok.Data;

@JsonIgnoreProperties({"password"})
@Data
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String avatar;
}
