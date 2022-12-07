package com.example.simpleblog.aspect;
/*
Aspect language (code) style using method name matching
 */

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
public class PostAuthorisationAspect extends ResourceAuthorisation {

    private final PostRepository postRepository;

    public PostAuthorisationAspect(UserRepository userRepository, PostRepository postRepository) {
        super(userRepository);
        this.postRepository = postRepository;
    }

    @Before("execution(* com.example.simpleblog.service.impl.PostServiceImpl.update*(..)) && args(postId, ..)")
    public void beforeAnyUpdateAdvice(UUID postId) {
        System.out.println("Aspect language style");
        UUID postOwnerId = getPostOwnerId(postId);
        if (!isOwner(postOwnerId)) {
            throw new BlogAPIException(HttpStatus.FORBIDDEN, AppConstants.UNAUTHORISED_UPDATE_MESSAGE);
        }
    }

    @Before("execution(* com.example.simpleblog.service.impl.PostServiceImpl.delete*(..)) && args(postId)")
    public void beforeAnyDeleteAdvice(UUID postId) {
        System.out.println("Aspect language style");
        UUID postOwnerId = getPostOwnerId(postId);
        if (!isOwner(postOwnerId)) {
            throw new BlogAPIException(HttpStatus.FORBIDDEN, AppConstants.UNAUTHORISED_DELETE_MESSAGE);
        }
    }

    private UUID getPostOwnerId(UUID id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return post.getUser().getId();
    }
}