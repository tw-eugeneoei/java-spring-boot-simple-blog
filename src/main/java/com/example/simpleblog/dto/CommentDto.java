package com.example.simpleblog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
public class CommentDto {
    private UUID id;

    @NotEmpty(message = "Field cannot be empty.")
    @Size(max = 255, message = "Field cannot have more than 255 characters.")
    private String content;

    private UUID postId;

    private Date createdAt = new Date();

    private UserDto user;
}
