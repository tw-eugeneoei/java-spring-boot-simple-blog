/* (C)2022 */
package com.example.simpleblog.aspect;

import com.example.simpleblog.entity.User;
import com.example.simpleblog.repository.UserRepository;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ResourceAuthorisation {
    public final UserRepository userRepository;

    public ResourceAuthorisation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isOwner(UUID ownerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        UUID userId = user.getId();
        return userId.equals(ownerId);
    }
}
