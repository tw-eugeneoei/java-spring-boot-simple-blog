/* (C)2022 */
package com.example.simpleblog.config;

import com.example.simpleblog.security.JwtAuthenticationEntryPoint;
import com.example.simpleblog.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity for method level security
@EnableGlobalMethodSecurity(
    prePostEnabled =
        true) // enables Spring Security pre/post annotations ie @PreAuthorize and @PostAuthorize
// annotations
public class SecurityConfig {

  @Autowired private UserDetailsService userDetailsService;

  @Autowired private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // The SecurityFilterChain bean defines which URL paths should be secured and which should not.
  @Bean
  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests(
            (authorize) ->
                authorize
                    .antMatchers(HttpMethod.GET, "/api/**")
                    .permitAll()
                    .antMatchers("/api/auth/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated() // All other paths must be authenticated.
            //                        .antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
            //                        .antMatchers("/api/v1/auth/**").permitAll()
            //                        .antMatchers("/v2/api-docs/**").permitAll()
            //                        .antMatchers("/swagger-ui/**").permitAll()
            //                        .antMatchers("/swagger-resources/**").permitAll()
            //                        .antMatchers("/swagger-ui.html").permitAll()
            //                        .antMatchers("/webjars/**").permitAll()
            );
    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
