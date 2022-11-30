package com.example.simpleblog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
public class PostDto {
    private UUID id;
    private Date createdAt  = new Date();

    @NotEmpty(message = "Field cannot be empty.")
    @Size(max = 255, message = "Field cannot have more than 255 characters.")
    private String content;

    private UserDto user;
}
