package com.example.simpleblog.dto;

import com.example.simpleblog.entity.Post;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CommentDto {
    private UUID id;
    private String content;
    private Post post;
    private Date createdAt;
}
