/* (C)2022 */
package com.example.simpleblog.controller;

import com.example.simpleblog.dto.CommentDto;
import com.example.simpleblog.dto.CommentResponse;
import com.example.simpleblog.service.CommentService;
import com.example.simpleblog.utils.AppConstants;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/posts/{postId}/comments")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(
            @PathVariable UUID postId, @Valid @RequestBody CommentDto commentDto) {
        CommentDto newComment = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CommentResponse> getComments(
            @PathVariable UUID postId,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false)
                    int pageSize) {
        CommentResponse commentResponse =
                commentService.getCommentsByPostId(postId, pageNo, pageSize);
        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("{commentId}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable UUID postId, @PathVariable UUID commentId) {
        CommentDto comment = commentService.getCommentById(postId, commentId);
        return ResponseEntity.ok(comment);
    }

    @PutMapping("{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(
            @PathVariable UUID postId,
            @PathVariable UUID commentId,
            @Valid @RequestBody CommentDto commentDto) {
        CommentDto comment = commentService.updateCommentById(postId, commentId, commentDto);
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<?> deleteCommentById(
            @PathVariable UUID postId, @PathVariable UUID commentId) {
        commentService.deleteCommentById(postId, commentId);
        return ResponseEntity.noContent().build();
    }
}
