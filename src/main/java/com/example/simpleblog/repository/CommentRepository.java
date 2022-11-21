package com.example.simpleblog.repository;

import com.example.simpleblog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findCommentsByPostId(UUID postId, Pageable pageable);
    Comment findByIdAndPostId(UUID commentId, UUID postId);
    Long deleteByIdAndPostId(UUID commentId, UUID postId);
}
