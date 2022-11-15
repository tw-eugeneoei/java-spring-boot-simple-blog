package com.example.simpleblog.controller;

import com.example.simpleblog.dto.PostDto;
import com.example.simpleblog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private PostService postService; // interface gets injected which leads to loose coupling

    // if class is configured as a spring bean and it has only one constructor, we can omit @Autowired annotation
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        PostDto post = postService.createPost(postDto);
        return new ResponseEntity<>(post, HttpStatus.CREATED);

        // // if you want to set the Location property in reponse headers
        // URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{postId}").buildAndExpand(post.getId()).toUri();
        // return ResponseEntity.created(location).body(post); // this sets "Location" headers of the new resource
    }
}
