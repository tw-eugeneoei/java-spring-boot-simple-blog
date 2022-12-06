package com.example.simpleblog.aspect;

import com.example.simpleblog.entity.User;
import com.example.simpleblog.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class ResourceAuthorisation implements IResourceAuthorisation {
    public final UserRepository userRepository;

    public ResourceAuthorisation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isOwner(UUID ownerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName());
        UUID userId = user.getId();
        return userId.equals(ownerId);
    }
}
