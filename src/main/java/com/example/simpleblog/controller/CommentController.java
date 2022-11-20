package com.example.simpleblog.controller;

import com.example.simpleblog.dto.CommentDto;
import com.example.simpleblog.dto.CommentResponse;
import com.example.simpleblog.service.CommentService;
import com.example.simpleblog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/posts/{postId}/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable UUID postId, @RequestBody CommentDto commentDto) {
        CommentDto newComment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CommentResponse> getComments(
            @PathVariable UUID postId,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize
    ) {
        CommentResponse commentResponse = commentService.getAllCommentsByPostId(
                postId,
                pageSize,
                Integer.parseInt(AppConstants.DEFAULT_PAGE_SIZE)
        );
        return ResponseEntity.ok(commentResponse);
    }
}
