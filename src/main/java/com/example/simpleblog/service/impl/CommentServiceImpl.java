/* (C)2022 */
package com.example.simpleblog.service.impl;

import com.example.simpleblog.aspect.IsCommentOwner;
import com.example.simpleblog.aspect.Logger;
import com.example.simpleblog.dto.CommentDto;
import com.example.simpleblog.dto.CommentResponse;
import com.example.simpleblog.entity.Comment;
import com.example.simpleblog.entity.Post;
import com.example.simpleblog.entity.User;
import com.example.simpleblog.exception.ResourceNotFoundException;
import com.example.simpleblog.repository.CommentRepository;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.repository.UserRepository;
import com.example.simpleblog.service.CommentService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final ModelMapper mapper;

  public CommentServiceImpl(
      CommentRepository commentRepository,
      PostRepository postRepository,
      UserRepository userRepository,
      ModelMapper mapper) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
    this.userRepository = userRepository;
    this.mapper = mapper;
  }

  @Override
  public CommentDto createComment(UUID postId, CommentDto commentDto) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userRepository.findByEmail(auth.getName());

    Comment comment = mapToEntity(commentDto);
    Post post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
    comment.setPost(post);
    comment.setUser(user);
    Comment newComment = commentRepository.save(comment);
    return mapToDTO(newComment);
  }

  // convert Comment DTO to Comment entity
  private Comment mapToEntity(CommentDto commentDto) {
    return mapper.map(commentDto, Comment.class);
  }

  // convert Comment entity to Comment DTO
  private CommentDto mapToDTO(Comment comment) {
    return mapper.map(comment, CommentDto.class);
  }

  @Override
  public CommentResponse getCommentsByPostId(UUID postId, int pageNo, int pageSize) {
    int offsetPageNo = pageNo - 1;
    Pageable pageable = PageRequest.of(offsetPageNo, pageSize, Sort.by("createdAt").descending());

    // is this necessary?
    // postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id",
    // postId));

    Page<Comment> comments = commentRepository.findCommentsByPostId(postId, pageable);
    List<Comment> listOfComments = comments.getContent();
    List<CommentDto> results =
        listOfComments.stream().map(this::mapToDTO).collect(Collectors.toList());

    return new CommentResponse(
        results,
        comments.getNumber() + 1,
        comments.getSize(),
        comments.getTotalElements(),
        comments.getTotalPages(),
        comments.hasNext());
  }

  @Override
  public CommentDto getCommentById(UUID postId, UUID commentId) {
    // method 1
    // Post post = postRepository.findById(postId).orElseThrow(() -> new
    // ResourceNotFoundException("Post", "id", postId));
    // Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new
    // ResourceNotFoundException("Comment", "id", commentId));

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

  @Override
  @IsCommentOwner(method = "update")
  @Logger(value = "apple")
  public CommentDto updateCommentById(UUID postId, UUID commentId, CommentDto commentDto) {
    Comment comment = commentRepository.findByIdAndPostId(commentId, postId);
    comment.setContent(commentDto.getContent());
    Comment updatedComment = commentRepository.save(comment);
    return mapToDTO(updatedComment);
  }

  @Override
  @IsCommentOwner(method = "delete")
  public void deleteCommentById(UUID postId, UUID commentId) {
    Comment comment = commentRepository.findByIdAndPostId(commentId, postId);
    commentRepository.delete(comment);

    //        Long deleted = commentRepository.deleteByIdAndPostId(commentId, postId);
    //        if (deleted == 0) {
    //            throw new ResourceNotFoundException("Comment", "id", commentId);
    //        }
  }
}
