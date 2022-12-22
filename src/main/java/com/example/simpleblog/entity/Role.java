/* (C)2022 */
package com.example.simpleblog.entity;

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
@Table(
    name = "roles",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Role {
  @Id // to indicate that this column is the primary key
  @Type(type = "uuid-char")
  @GeneratedValue(generator = "UUID")
  private UUID id;

  @Column(nullable = false)
  private String name;
}
