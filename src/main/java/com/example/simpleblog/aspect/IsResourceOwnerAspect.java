/* (C)2022 */
package com.example.simpleblog.aspect;

import com.example.simpleblog.entity.User;
import com.example.simpleblog.exception.BlogAPIException;
import com.example.simpleblog.repository.UserRepository;
import com.example.simpleblog.utils.AppConstants;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class IsResourceOwnerAspect<E> {

  private final JpaRepository<E, UUID> repository;
  private final UserRepository userRepository;
  private final String resource;

  public IsResourceOwnerAspect(
      JpaRepository<E, UUID> repository, UserRepository userRepository, String resource) {
    this.repository = repository;
    this.userRepository = userRepository;
    this.resource = resource;
  }

  public final E getResource(UUID id) {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new BlogAPIException(
                    HttpStatus.NOT_FOUND,
                    String.format("%s resource with id: \"%s\" not found.", resource, id)));
  }

  public final boolean isResourceOwner(UUID ownerId) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    User user = userRepository.findByEmail(auth.getName());
    UUID userId = user.getId();
    return userId.equals(ownerId);
  }

  public final void validateOwnership(UUID ownerId, String method) {
    if (!isResourceOwner(ownerId)) {
      throw new BlogAPIException(
          HttpStatus.FORBIDDEN, AppConstants.formatUnauthorisedMessage(method));
    }
  }
}
