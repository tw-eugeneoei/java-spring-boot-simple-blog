package com.example.simpleblog.controller;

import com.example.simpleblog.dto.PostDto;
import com.example.simpleblog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private PostService postService; // interface gets injected which leads to loose coupling

    // if class is configured as a spring bean and it has only one constructor, we can omit @Autowired annotation
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto post = postService.createPost(postDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
        // // if you want to set the Location property in reponse headers
        // URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{postId}").buildAndExpand(post.getId()).toUri();
        // return ResponseEntity.created(location).body(post); // this sets "Location" headers of the new resource
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts(
            @RequestParam(defaultValue = "1", required = false) int pageNo,
            @RequestParam(defaultValue = "10", required = false) int pageSize
    ) {
        List<PostDto> posts = postService.getPosts(pageNo, pageSize);
        // return new ResponseEntity<>(posts, HttpStatus.OK);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable UUID postId) {
        PostDto post = postService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("{postId}")
    public ResponseEntity<PostDto> updatePostById(@PathVariable UUID postId, @RequestBody PostDto postDto) {
        PostDto post = postService.updatePostById(postId, postDto);
        return ResponseEntity.ok(post);
    }

    @DeleteMapping("{postId}")
    public ResponseEntity<?> deletePostById(@PathVariable UUID postId) {
        postService.deletePostById(postId);
        return ResponseEntity.noContent().build();
    }
}
