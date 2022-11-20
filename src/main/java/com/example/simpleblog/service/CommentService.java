package com.example.simpleblog.service;

import com.example.simpleblog.dto.CommentDto;
import com.example.simpleblog.dto.CommentResponse;

import java.util.UUID;

public interface CommentService {
    CommentDto createComment(UUID postId, CommentDto comment);
    // CommentResponse getComments(UUID postId, int pageNo, int pageSize);
    // CommentDto getCommentById(UUID postId, UUID commentId);
    // CommentDto updateCommentById(UUID postId, UUID commentId, CommentDto comment);
    // void deleteCommentById(UUID commentId);
}
