package com.example.simpleblog.service.impl;

import com.example.simpleblog.dto.PostDto;
import com.example.simpleblog.dto.PostResponse;
import com.example.simpleblog.entity.Post;
import com.example.simpleblog.exception.ResourceNotFoundException;
import com.example.simpleblog.repository.PostRepository;
import com.example.simpleblog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// @Service annotation indicates that the class is a service class, and it is available
// for auto-detection during component scanning and will be injected or autowired to other classes
@Service
public class PostServiceImpl implements PostService {

    // if class is configured as a spring bean, and it has only one constructor, we can omit @Autowired annotation
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    // constructor based dependency injection
    private final PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        // convert dto to entity and save into db
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post); // save returns an entity

        // convert entity to dto before returning to controller
        return mapToDTO(newPost);
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
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        int offsetPageNo = pageNo - 1; // Pageable is zero based pagination
        // Pageable pageable = PageRequest.of(offsetPageNo, pageSize); // create Pageable instance with page number and page size
        // Pageable pageable = PageRequest.of(offsetPageNo, pageSize, Sort.by(sortBy)); // create Pageable instance with page number, page size, and sort value
        // create Pageable instance with page number, page size, and sort value with sort direction
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(offsetPageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable); // returns a page of entities
        List<Post> listOfPosts = posts.getContent(); // get content from page object

        // List<PostDto> results = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        List<PostDto> results = listOfPosts.stream().map(this::mapToDTO).collect(Collectors.toList()); // same as above

        PostResponse postResponse = new PostResponse();
        postResponse.setResults(results);
        postResponse.setPageNo(posts.getNumber() + 1);
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalCount(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setHasNextPage(posts.hasNext());
        return postResponse;
    }

    @Override
    public PostDto getPostById(UUID id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePostById(UUID id, PostDto postDto) {
        // get post by id
        // if post does not exist, throw exception
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(UUID id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);

        // postRepository.deleteById(id);
    }
}
