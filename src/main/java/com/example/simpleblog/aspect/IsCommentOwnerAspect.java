/* (C)2022 */
package com.example.simpleblog.aspect;

import com.example.simpleblog.entity.Comment;
import com.example.simpleblog.repository.CommentRepository;
import com.example.simpleblog.repository.UserRepository;
import java.util.UUID;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class IsCommentOwnerAspect extends IsResourceOwnerAspect<Comment> {

  public IsCommentOwnerAspect(CommentRepository commentRepository, UserRepository userRepository) {
    super(commentRepository, userRepository, "Comment");
  }

  //    @Before(value = "@annotation(isCommentOwner) && args(postId, commentId, ..)", argNames =
  // "isCommentOwner, postId, commentId")
  //    public void updateAndDeletePostAuthorisationAdvice(IsCommentOwner isCommentOwner, UUID
  // postId, UUID commentId) {
  //        UUID commentOwnerId = getResource(commentId).getUser().getId();
  //        validateOwnership(commentOwnerId, isCommentOwner.method());
  //    }

  @Pointcut(
      value = "@annotation(isCommentOwner) && args(postId, commentId, ..)",
      argNames = "isCommentOwner, postId, commentId")
  public void updateAndDelete(IsCommentOwner isCommentOwner, UUID postId, UUID commentId) {}

  @Before("updateAndDelete(isCommentOwner, postId, commentId)")
  public void updateAndDeletePostAuthorisationAdvice(
      IsCommentOwner isCommentOwner, UUID postId, UUID commentId) {
    System.out.println("\n>>> Executing aspect\n");
    UUID commentOwnerId = getResource(commentId).getUser().getId();
    validateOwnership(commentOwnerId, isCommentOwner.method());
  }

  @After("updateAndDelete(isCommentOwner, postId, commentId)")
  public void afterUpdateAndDeletePostAuthorisationAdvice(
      IsCommentOwner isCommentOwner, UUID postId, UUID commentId) {
    System.out.println("AFTER ADVICE => Reusing pointcut expression");
  }
}
