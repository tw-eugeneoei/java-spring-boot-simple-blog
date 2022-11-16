package com.example.simpleblog.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PostDto {
    private UUID id;
    private String title;
    private String description;
    private String content;
}