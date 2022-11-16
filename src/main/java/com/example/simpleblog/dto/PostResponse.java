package com.example.simpleblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> results;
    private int pageNo;
    private int pageSize;
    private long totalCount;
    private long totalPages;
    private boolean hasNextPage;
}
