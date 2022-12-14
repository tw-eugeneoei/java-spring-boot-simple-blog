/* (C)2022 */
package com.example.simpleblog.entity;

import java.util.*;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

// @Data generates getters for all fields, a useful toString method and hashCode and equals
// implementations that check
// all non-transient fields. WIll also generate setters for all non-final fields, as well as a
// constructor
// @Data // will cause infinite loop when populated nested resources due to toString method
@Getter
@Setter
@AllArgsConstructor
// when we create a JPA entity with an argument constructor then we should also need a no argument
// constructor
// because Hibernate internally uses proxies to create objects
@NoArgsConstructor
// @Entity to map JPA entity to MySQL database table
@Entity
@Table(name = "posts")
public class Post {
    @Id // to indicate that this column is the primary key
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false)
    private Date createdAt;

    @Column(nullable = false)
    private String content;

    // orphanRemoval means when parent is removed, child is also removed
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
