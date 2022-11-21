package com.example.simpleblog.service.impl;

import com.example.simpleblog.dto.CommentDto;
import com.example.simpleblog.dto.CommentResponse;
import com.example.simpleblog.dto.PostDto;
import com.example.simpleblog.entity.Comment;
import com.example.simpleblog.entity.Post;
import com.example.simpleblog.exception.BlogAPIException;
import com.example.simpleblog.exception.ResourceNotFoundException;
import com.example.simpleblog.repository.CommentRepository;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.service.CommentService;
import com.example.simpleblog.utils.AppConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    // TODO: paginate comments
    @Override
    public CommentResponse getAllCommentsByPostId(UUID postId, int pageNo, int pageSize) {
        int offsetPageNo = pageNo - 1;
        Pageable pageable = PageRequest.of(
                offsetPageNo,
                pageSize,
                Sort.by("createdAt").descending()
        );
//        Page<Comment> comments = commentRepository.findAll(pageable);
//        List<Comment> listOfComments = comments.getContent();

//        List<CommentDto> results = listOfComments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());

        List<Comment> listOfComments = commentRepository.findAllCommentsByPostId(postId);
        List<CommentDto> results = listOfComments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());

        CommentResponse commentResponse = new CommentResponse(
                results,
                1,
                1,
                1,
                1,
                false
//                comments.getNumber() + 1,
//                comments.getSize(),
//                comments.getTotalElements(),
//                comments.getTotalPages(),
//                comments.hasNext()
        );
        return commentResponse;
    }

    @Override
    public CommentDto getCommentById(UUID postId, UUID commentId) {
        // method 1
        // Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        // Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        // boolean doesCommentBelongToPost = comment.getPost().getId().equals(post.getId());
        // if (!doesCommentBelongToPost) {
        //     throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        // }
        // return mapToDTO(comment);

        // method 2
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId);
        if (comment == null) {
            throw new ResourceNotFoundException("Comment", "id", commentId);
        }
        return mapToDTO(comment);
    }
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
