package com.example.simpleblog.aspect;

import com.example.simpleblog.entity.Comment;
import com.example.simpleblog.repository.CommentRepository;
import com.example.simpleblog.repository.UserRepository;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class IsCommentOwnerAspect extends IsResourceOwnerAspect<Comment> {

    public IsCommentOwnerAspect(CommentRepository commentRepository, UserRepository userRepository) {
        super(commentRepository, userRepository, "Comment");
    }

//    @Before(value = "@annotation(isCommentOwner) && args(postId, commentId, ..)", argNames = "isCommentOwner, postId, commentId")
//    public void updateAndDeletePostAuthorisationAdvice(IsCommentOwner isCommentOwner, UUID postId, UUID commentId) {
//        UUID commentOwnerId = getResource(commentId).getUser().getId();
//        validateOwnership(commentOwnerId, isCommentOwner.method());
//    }

    @Pointcut(
            value = "@annotation(isCommentOwner) && args(postId, commentId, ..)",
            argNames = "isCommentOwner, postId, commentId"
    )
    public void updateAndDelete(IsCommentOwner isCommentOwner, UUID postId, UUID commentId) {}

    @Before("updateAndDeleteAdvice(isCommentOwner, postId, commentId)")
    public void updateAndDeletePostAuthorisationAdvice(IsCommentOwner isCommentOwner, UUID postId, UUID commentId) {
        UUID commentOwnerId = getResource(commentId).getUser().getId();
        validateOwnership(commentOwnerId, isCommentOwner.method());
    }

    @After("updateAndDeleteAdvice(isCommentOwner, postId, commentId)")
    public void afterUpdateAndDeletePostAuthorisationAdvice(IsCommentOwner isCommentOwner, UUID postId, UUID commentId) {
        System.out.println("AFTER ADVICE => Reusing pointcut expression");
    }
}
