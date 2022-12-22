/* (C)2022 */
package com.example.simpleblog.dto;

import java.util.Date;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
  private UUID id;
  private Date createdAt = new Date();

  @NotEmpty(message = "Field cannot be empty.")
  @Size(max = 255, message = "Field cannot have more than 255 characters.")
  private String content;

  private UserDto user;
}
