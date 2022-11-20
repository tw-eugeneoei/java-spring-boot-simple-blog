package com.example.simpleblog.repository;

import com.example.simpleblog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllCommentsByPostId(UUID postId);
}
