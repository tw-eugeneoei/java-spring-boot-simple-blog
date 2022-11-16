package com.example.simpleblog.service.impl;

import com.example.simpleblog.dto.PostDto;
import com.example.simpleblog.entity.Post;
import com.example.simpleblog.exception.ResourceNotFoundException;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post); // save returns an entity

        // convert entity to dto before returning to controller
        PostDto postResponse = mapToDTO(newPost);
        return postResponse;
    }

    // convert Post DTO to Post entity
    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    // convert Post entity to Post DTO
    private PostDto mapToDTO(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    @Override
    public List<PostDto> getPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(UUID id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

}
