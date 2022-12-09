package com.example.simpleblog.aspect;

import com.example.simpleblog.entity.Comment;
import com.example.simpleblog.exception.BlogAPIException;
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
public class IsCommentOwnerAspect extends IsResourceOwnerAspect<Comment> {

    public IsCommentOwnerAspect(CommentRepository commentRepository, UserRepository userRepository) {
        super(commentRepository, userRepository, "Comment");
    }

    @Before(value = "@annotation(isCommentOwner) && args(postId, commentId, ..)", argNames = "isCommentOwner, postId, commentId")
    public void updateAndDeletePostAuthorisationCheck(IsCommentOwner isCommentOwner, UUID postId, UUID commentId) {
        UUID commentOwnerId = getResource(commentId).getUser().getId();
        validateOwnership(commentOwnerId, isCommentOwner.method());
    }
}
