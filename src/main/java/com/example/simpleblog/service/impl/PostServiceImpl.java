package com.example.simpleblog.service.impl;

import com.example.simpleblog.dto.PostDto;
import com.example.simpleblog.entity.Post;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.service.PostService;
import org.springframework.stereotype.Service;

// @Service annotation indicates that the class is a service class and it is available
// for autodetection during component scanning and will be injected or autowired to other classes
@Service
public class PostServiceImpl implements PostService {

    // if class is configured as a spring bean and it has only one constructor, we can omit @Autowired annotation
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // constructor based dependency injection
    private PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        // convert dto to entity and save into db
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post newPost = postRepository.save(post); // save returns an entity

        // convert entity to dto before returning to controller
        PostDto postResponse = new PostDto();
        postResponse.setId(newPost.getId());
        postResponse.setTitle(newPost.getTitle());
        postResponse.setDescription(newPost.getDescription());
        postResponse.setContent(newPost.getContent());
        return postResponse;
    }
}
