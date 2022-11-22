package com.example.simpleblog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Comment {
    @Id // to indicate that this column is the primary key
    @Type(type = "uuid-char")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false) // if @Column annotation is not used, JPA automatically uses property as column name
    private String content;

    // FetchType.lazy fetches only the related entities from the db when you use the relationship
    @ManyToOne(fetch = FetchType.LAZY)
    // to specify foreign key
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(nullable = false)
    private Date createdAt;
}
