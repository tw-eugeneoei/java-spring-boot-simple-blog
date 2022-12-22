/* (C)2022 */
package com.example.simpleblog.repository;

import com.example.simpleblog.entity.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmail(String email);

    Optional<User> findByEmailOrUsername(String email, String username);

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmailOrUsername(String email, String username);
}
