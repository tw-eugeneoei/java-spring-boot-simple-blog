/* (C)2022 */
package com.example.simpleblog.repository;

import com.example.simpleblog.entity.Post;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository annotation is not necessary because JpaRepository interface has an implementation
// class called SimpleJpaRepository
// and it internally is already annotated with @Repository and @Transactional annotations
// public interface PostRepository extends JpaRepository<Post, Long> {
public interface PostRepository extends JpaRepository<Post, UUID> {}
