/* (C)2022 */
package com.example.simpleblog.service.impl;

import com.example.simpleblog.entity.Role;
import com.example.simpleblog.entity.User;
import com.example.simpleblog.exception.BlogAPIException;
import com.example.simpleblog.repository.UserRepository;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
        User user =
                userRepository
                        .findByEmailOrUsername(emailOrUsername, emailOrUsername)
                        // .orElseThrow(() -> new UsernameNotFoundException("User not found with
                        // email or
                        // username:" + emailOrUsername));
                        .orElseThrow(
                                () ->
                                        new BlogAPIException(
                                                HttpStatus.BAD_REQUEST,
                                                "Invalid email or username."));

        // convert User entity object into spring security provided user
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                // spring security user object accepts collection of GrantedAuthority. But User
                // entity
                // object is a Set<Role>
                // therefore we need to convert Set<Role> into Collection of GrantedAuthority
                mapUserRolesToGrantedAuthority(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapUserRolesToGrantedAuthority(Set<Role> roles) {
        // SimpleGrantedAuthority implements GrantedAuthority interface
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
