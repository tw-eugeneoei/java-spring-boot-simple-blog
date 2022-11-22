package com.example.simpleblog.dto;

import com.example.simpleblog.entity.Post;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

// @JsonIgnore
// @JsonSetter or @JsonGetter

@Data
public class CommentDto {
    private UUID id;
    private String content;
    private UUID postId;
    // private Post post;
    // private PostDto post;
    private Date createdAt = new Date();
}
