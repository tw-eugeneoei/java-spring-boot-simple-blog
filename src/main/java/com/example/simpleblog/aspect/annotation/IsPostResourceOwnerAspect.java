package com.example.simpleblog.aspect.annotation;
/*
@AspectJ annotation style
 */

import com.example.simpleblog.aspect.ResourceAuthorisation;
import com.example.simpleblog.entity.Post;
import com.example.simpleblog.exception.BlogAPIException;
import com.example.simpleblog.exception.ResourceNotFoundException;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.repository.UserRepository;
import com.example.simpleblog.utils.AppConstants;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class IsPostResourceOwnerAspect extends ResourceAuthorisation {

    private final PostRepository postRepository;

    public IsPostResourceOwnerAspect(UserRepository userRepository, PostRepository postRepository) {
        super(userRepository);
        this.postRepository = postRepository;
    }

//    // because aspect and annotation are defined in different packages, use fully qualified class name of the annotation class in pointcut expression
//    // this approach if NO need for retrieving annotation property
//    // @Before("@annotation(com.example.simpleblog.aspect.annotation.IsPostOwner) && args(postId, ..)")
//    @Before("@annotation(IsPostOwner) && args(postId, ..)")
//    public void updateAndDeletePostAuthorisationCheck(UUID postId) {
//        System.out.println("AspectJ annotation style");
//        UUID postOwnerId = getPostOwnerId(postId);
//        if (!isOwner(postOwnerId)) {
//            throw new BlogAPIException(HttpStatus.FORBIDDEN, "Unauthorised.");
//        }
//    }

    // "&& args(postId, ..)" to retrieve arguments from method using the annotation and ".." is a wildcard
    // "argNames" to set the argument names and the order will match that of arguments in method
    // this approach will require first argument in advice and first value in "argNames" to be an annotation type
    // this approach to retrieve annotation property
    @Before(value = "@annotation(asd) && args(postId, ..)", argNames = "asd, postId")
    public void updateAndDeletePostAuthorisationCheck(IsPostResourceOwner isPostResourceOwner, UUID postIdd) {
        System.out.println("AspectJ annotation style");
        UUID postOwnerId = getPostOwnerId(postIdd);
        if (!isOwner(postOwnerId)) {
            throw new BlogAPIException(HttpStatus.FORBIDDEN, AppConstants.formatUnauthorisedMessage(isPostResourceOwner.method()));
        }
    }

    private UUID getPostOwnerId(UUID id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return post.getUser().getId();
    }
}
