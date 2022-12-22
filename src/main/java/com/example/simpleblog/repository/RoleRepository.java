/* (C)2022 */
package com.example.simpleblog.repository;

import com.example.simpleblog.entity.Role;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, UUID> {
  Optional<Role> findByName(String name);
}
