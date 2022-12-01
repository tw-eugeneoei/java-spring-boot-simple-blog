package com.example.simpleblog.controller;

import com.example.simpleblog.dto.PostDto;
import com.example.simpleblog.dto.PostResponse;
import com.example.simpleblog.service.PostService;
import com.example.simpleblog.utils.AppConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private final PostService postService; // interface gets injected which leads to loose coupling

    // if class is configured as a spring bean and it has only one constructor, we can omit @Autowired annotation
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        PostDto post = postService.createPost(postDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
        // // if you want to set the Location property in response headers
        // URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{postId}").buildAndExpand(post.getId()).toUri();
        // return ResponseEntity.created(location).body(post); // this sets "Location" headers of the new resource
    }

    @GetMapping
    public ResponseEntity<PostResponse> getPosts(
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
    ) {
        PostResponse postResponse = postService.getPosts(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable UUID postId, Authentication authentication) {
        PostDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("{postId}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable UUID postId, @Valid @RequestBody PostDto postDto) {
        PostDto post = postService.updatePostById(postId, postDto);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable UUID postId) {
        postService.deletePostById(postId);
        return ResponseEntity.noContent().build();
    }
}
