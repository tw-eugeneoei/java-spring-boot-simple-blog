/* (C)2022 */
package com.example.simpleblog.aspect;

import com.example.simpleblog.entity.Post;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.repository.UserRepository;
import java.util.UUID;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class IsPostOwnerAspect extends IsResourceOwnerAspect<Post> {

  public IsPostOwnerAspect(PostRepository postRepository, UserRepository userRepository) {
    super(postRepository, userRepository, "Post");
  }

  //    // because aspect and annotation are defined in different packages, use fully qualified
  // class name of the annotation class in pointcut expression
  //    // this approach if NO need for retrieving annotation property
  //    // @Before("@annotation(com.example.simpleblog.aspect.annotation.IsPostOwner) &&
  // args(postId, ..)")
  //    @Before("@annotation(IsPostOwner) && args(postId, ..)")
  //    public void updateAndDeletePostAuthorisationCheck(UUID postId) {
  //        System.out.println("AspectJ annotation style");
  //        UUID postOwnerId = getPostOwnerId(postId);
  //        if (!isOwner(postOwnerId)) {
  //            throw new BlogAPIException(HttpStatus.FORBIDDEN, "Unauthorised.");
  //        }
  //    }

  // "&& args(postId, ..)" to retrieve arguments from method using the annotation and ".." is a
  // wildcard
  // "argNames" to set the argument names and the order will match that of arguments in method
  // this approach will require first argument in advice and first value in "argNames" to be an
  // annotation type
  // this approach to retrieve annotation property
  @Before(value = "@annotation(asd) && args(id, ..)", argNames = "asd, id")
  public void updateAndDeletePostAuthorisationAdvice(IsPostOwner isPostResourceOwner, UUID id) {
    UUID postOwnerId = getResource(id).getUser().getId();
    validateOwnership(postOwnerId, isPostResourceOwner.method());
  }
}
