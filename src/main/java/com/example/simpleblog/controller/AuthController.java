/* (C)2022 */
package com.example.simpleblog.controller;

import com.example.simpleblog.dto.JwtAuthResponse;
import com.example.simpleblog.dto.LoginDto;
import com.example.simpleblog.dto.RegisterDto;
import com.example.simpleblog.dto.UserDto;
import com.example.simpleblog.entity.Role;
import com.example.simpleblog.entity.User;
import com.example.simpleblog.exception.BlogAPIException;
import com.example.simpleblog.repository.RoleRepository;
import com.example.simpleblog.repository.UserRepository;
import com.example.simpleblog.security.JwtTokenProvider;
import com.example.simpleblog.utils.AppConstants;
import java.util.Collections;
import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("api/auth")
public class AuthController {
  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private UserRepository userRepository;

  @Autowired private RoleRepository roleRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @Autowired private ModelMapper mapper;

  @PostMapping("login")
  public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getEmailOrUsername(), loginDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // get token from jwtTokenProvider
    String token = jwtTokenProvider.generateToken(authentication);
    User user = userRepository.findByEmail(loginDto.getEmailOrUsername());
    return ResponseEntity.ok(new JwtAuthResponse(token, mapToDto(user)));

    // return ResponseEntity.ok("Login success");
  }

  private UserDto mapToDto(User user) {
    return mapper.map(user, UserDto.class);
  }

  @PostMapping("register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterDto registerDto) {
    if (!doesPasswordAndConfirmPasswordMatch(
        registerDto.getPassword(), registerDto.getConfirmPassword())) {
      throw new BlogAPIException(
          HttpStatus.BAD_REQUEST, "Password and Confirm Password do not match");
    }

    // check if email exist
    if (userRepository.existsByEmail(registerDto.getEmail())) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Email already exist.");
    }

    // check if username exist
    if (userRepository.existsByUsername(registerDto.getUsername())) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exist.");
    }

    User user = new User();
    user.setEmail(registerDto.getEmail());
    user.setUsername(registerDto.getUsername());
    user.setFirstName(registerDto.getFirstName());
    user.setLastName(registerDto.getLastName());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

    // Role role = roleRepository.findByName(AppConstants.ROLE_USER).get();
    Role role = roleRepository.findByName(AppConstants.ROLE_USER).orElse(null);
    user.setRoles(Collections.singleton(role));

    userRepository.save(user);

    // Map<String, String> response = new HashMap<>();
    // response.put("message", "User created.");
    // return ResponseEntity.ok(response);
    return ResponseEntity.noContent().build();
  }

  private Boolean doesPasswordAndConfirmPasswordMatch(String password, String confirmPassword) {
    return password.equals(confirmPassword);
  }
}
