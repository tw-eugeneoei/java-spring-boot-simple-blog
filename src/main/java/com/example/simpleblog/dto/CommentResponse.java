/* (C)2022 */
package com.example.simpleblog.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
  private List<CommentDto> results;
  private int pageNo;
  private int pageSize;
  private long totalCount;
  private long totalPages;
  private boolean hasNextPage;
}
