package com.example.simpleblog.service;

import com.example.simpleblog.dto.PostDto;

import java.util.List;
import java.util.UUID;

public interface PostService {

    PostDto createPost(PostDto post);

    List<PostDto> getPosts(int pageNo, int pageSize);

    PostDto getPostById(UUID id);

    PostDto updatePostById(UUID id, PostDto post);

    void deletePostById(UUID id);
}
