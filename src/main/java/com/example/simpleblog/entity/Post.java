package com.example.simpleblog.entity;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

// @Data generates getters for all fields, a useful toString method and hashCode and equals implementations that check
// all non-transient fields. WIll also generate setters for all non-final fields, as well as a constructor
// @Data // will cause infinite loop when populated nested resources due to toString method
@Getter
@Setter
@AllArgsConstructor
// when we create a JPA entity with an argument constructor then we should also need a no argument constructor
// because Hibernate internally uses proxies to create objects
@NoArgsConstructor
// @Entity to map JPA entity to MySQL database table
@Entity
@Table(
        name = "posts",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})}
)
public class Post {
    @Id // to indicate that this column is the primary key
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String content;

    // orphanRemoval means when parent is removed, child is also removed
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    // set does not allow duplicates
    private Set<Comment> comments = new HashSet<>();
}
