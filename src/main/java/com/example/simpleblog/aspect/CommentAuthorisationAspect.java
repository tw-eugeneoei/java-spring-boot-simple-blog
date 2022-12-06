package com.example.simpleblog.aspect;

import com.example.simpleblog.entity.Comment;
import com.example.simpleblog.exception.BlogAPIException;
import com.example.simpleblog.exception.ResourceNotFoundException;
import com.example.simpleblog.repository.CommentRepository;
import com.example.simpleblog.repository.UserRepository;
import com.example.simpleblog.utils.AppConstants;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class CommentAuthorisationAspect extends ResourceAuthorisation {

    private final CommentRepository commentRepository;

    public CommentAuthorisationAspect(UserRepository userRepository, CommentRepository commentRepository) {
        super(userRepository);
        this.commentRepository = commentRepository;
    }

    @Before("execution(* com.example.simpleblog.service.impl.CommentServiceImpl.update*(..)) && args(postId, commentId, ..)")
    public void beforeUpdateAnyAdvice(UUID postId, UUID commentId) {
        System.out.println(">>>> update comment AOP");
        UUID commentOwnerId = getCommentOwnerId(postId, commentId);
        if (!isOwner(commentOwnerId)) {
            System.out.println(">>>> update comment AOP forbidden success");
            throw new BlogAPIException(HttpStatus.FORBIDDEN, AppConstants.UNAUTHORISED_UPDATE_MESSAGE);
        }
    }

    @Before("execution(* com.example.simpleblog.service.impl.CommentServiceImpl.delete*(..)) && args(postId, commentId, ..)")
    public void beforeDeleteAnyAdvice(UUID postId, UUID commentId) {
        System.out.println(">>>> delete comment AOP");
        UUID commentOwnerId = getCommentOwnerId(postId, commentId);
        if (!isOwner(commentOwnerId)) {
            System.out.println(">>>> delete comment AOP forbidden success");
            throw new BlogAPIException(HttpStatus.FORBIDDEN, AppConstants.UNAUTHORISED_DELETE_MESSAGE);
        }
    }

    private UUID getCommentOwnerId(UUID postId, UUID commentId) {
        Comment comment = commentRepository.findByIdAndPostId(commentId, postId);
        if (comment == null) {
            throw new ResourceNotFoundException("Comment", "id", commentId);
        } else {
            return comment.getUser().getId();
        }
    }
}
