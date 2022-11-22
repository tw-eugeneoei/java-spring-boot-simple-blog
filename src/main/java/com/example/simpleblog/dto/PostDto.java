package com.example.simpleblog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Data
public class PostDto {
    private UUID id;

    @NotEmpty(message = "Field cannot be empty.")
    @Size(min = 2, message = "Field must have at least 2 characters.")
    private String title;

    @NotEmpty(message = "Field cannot be empty.")
    private String description;

    @NotEmpty(message = "Field cannot be empty.")
    private String content;

    private List<CommentDto> comments;
}
