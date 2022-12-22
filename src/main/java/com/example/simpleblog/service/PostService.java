/* (C)2022 */
package com.example.simpleblog.service;

import com.example.simpleblog.dto.PostDto;
import com.example.simpleblog.dto.PostResponse;
import java.util.UUID;

public interface PostService {

  PostDto createPost(PostDto post);

  PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir);

  PostDto getPostById(UUID id);

  PostDto updatePostById(UUID id, PostDto post);

  void deletePostById(UUID id);
}
