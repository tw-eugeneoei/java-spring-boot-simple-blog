package com.example.simpleblog.controller;

import com.example.simpleblog.dto.JwtAuthResponse;
import com.example.simpleblog.dto.LoginDto;
import com.example.simpleblog.dto.RegisterDto;
import com.example.simpleblog.entity.Role;
import com.example.simpleblog.entity.User;
import com.example.simpleblog.repository.RoleRepository;
import com.example.simpleblog.repository.UserRepository;
import com.example.simpleblog.security.JwtTokenProvider;
import com.example.simpleblog.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmailOrUsername(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token from jwtTokenProvider
        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(token));

        // return ResponseEntity.ok("Login success");
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {
        // check if email exist
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exist.");
        }

        // check if username exist
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exist.");
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setUsername(registerDto.getUsername());
        user.setFirstName(registerDto.getFirstName());
        user.setLastName(registerDto.getLastName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role role = roleRepository.findByName(AppConstants.ROLE_USER).get();
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);

        // Map<String, String> response = new HashMap<>();
        // response.put("message", "User created.");
        // return ResponseEntity.ok(response);
        return ResponseEntity.noContent().build();

    }
}
