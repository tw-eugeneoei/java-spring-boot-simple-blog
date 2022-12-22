/* (C)2022 */
package com.example.simpleblog.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
  @Id // to indicate that this column is the primary key
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(nullable = false)
  private Date createdAt;

  @Column(
      nullable =
          false) // if @Column annotation is not used, JPA automatically uses property as column
  // name
  private String content;

  // FetchType.lazy fetches only the related entities from the db when you use the relationship
  // fetches on demand
  @ManyToOne(fetch = FetchType.LAZY)
  // to specify foreign key
  @JoinColumn(name = "post_id", nullable = false)
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
