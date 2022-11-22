package com.example.simpleblog.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

// @JsonIgnore
// @JsonSetter or @JsonGetter

//@JsonIgnore
//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

@Data
public class CommentDto {
    private UUID id;

    @NotEmpty(message = "Field cannot be empty.")
    @Size(min = 2, message = "Field must have at least 2 characters.")
    private String content;

    private UUID postId;

    private Date createdAt = new Date();
}
