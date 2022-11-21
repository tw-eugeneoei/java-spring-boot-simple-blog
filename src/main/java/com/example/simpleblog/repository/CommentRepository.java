package com.example.simpleblog.repository;

import com.example.simpleblog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findCommentsByPostId(UUID postId, Pageable pageable);
    Comment findByIdAndPostId(UUID commentId, UUID postId);

    // Persisting and deleting objects in JPA requires a transaction. That's why we should use a @Transactional
    // annotation when using these derived delete queries, to make sure a transaction is running.
    @Transactional
    Long deleteByIdAndPostId(UUID commentId, UUID postId);
}
