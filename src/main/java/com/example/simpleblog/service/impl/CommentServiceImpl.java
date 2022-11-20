package com.example.simpleblog.service.impl;

import com.example.simpleblog.dto.CommentDto;
import com.example.simpleblog.dto.CommentResponse;
import com.example.simpleblog.dto.PostDto;
import com.example.simpleblog.entity.Comment;
import com.example.simpleblog.entity.Post;
import com.example.simpleblog.exception.ResourceNotFoundException;
import com.example.simpleblog.repository.CommentRepository;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(UUID postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
        // comment.setCreatedAt(new Date());
        Comment newComment = commentRepository.save(comment);
        return mapToDTO(newComment);
    }

    // convert Comment DTO to Comment entity
    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setContent(commentDto.getContent());
        return comment;
    }

    // convert Comment entity to Comment DTO
    private CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setCreatedAt(comment.getCreatedAt());
        return commentDto;
    }

//    @Override
//    public CommentResponse getComments(UUID postId, int pageNo, int pageSize) {
//        return null;
//    }
//
//    @Override
//    public CommentDto getCommentById(UUID postId, UUID commentId) {
//        return null;
//    }
//
//    @Override
//    public CommentDto updateCommentById(UUID postId, UUID commentId, CommentDto comment) {
//        return null;
//    }
//
//    @Override
//    public void deleteCommentById(UUID commentId) {
//
//    }
}
