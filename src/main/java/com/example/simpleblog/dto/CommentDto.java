package com.example.simpleblog.dto;

// import com.example.simpleblog.entity.Post;
// import com.fasterxml.jackson.annotation.JsonIgnore;
// import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

// @JsonIgnore
// @JsonSetter or @JsonGetter

//@JsonIgnore
//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)

@Data
public class CommentDto {
    private UUID id;
    private String content;
    private UUID postId;
    // private Post post;
    // private PostDto post;
    private Date createdAt = new Date();
}
