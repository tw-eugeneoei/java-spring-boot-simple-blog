package com.example.simpleblog.config;

import com.example.simpleblog.security.JwtAuthenticationEntryPoint;
import com.example.simpleblog.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;


//@Configuration
//@EnableWebSecurity
//// to customise default spring auto configuration, extend WebSecurityConfigurerAdapter
//// todo: migrate WebSecurityConfigurerAdapter
//// @EnableGlobalMethodSecurity for method level security
//@EnableGlobalMethodSecurity(prePostEnabled = true) // enables Spring Security pre/post annotations ie @PreAuthorize and @PostAuthorize annotations
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }
//
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .disable()
//                // whenever there is unauthorised access from client, application throws unauthorised exception and will be handled by jwtAuthenticationEntryPoint
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .and()
//                // because we are using JWT, set stateless session policy
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
//                .antMatchers("/api/auth/**").permitAll()
//                .anyRequest()
//                .authenticated();
//
//        // add filter to sprint security configuration
//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//    }
//
////    @Override
////    @Bean
////    protected UserDetailsService userDetailsService() {
////        UserDetails jon = User.builder().username("jon").password(passwordEncoder().encode("jon")).roles("USER").build();
////        UserDetails admin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
////        return new InMemoryUserDetailsManager(jon, admin);
////    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//}

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity for method level security
@EnableGlobalMethodSecurity(prePostEnabled = true) // enables Spring Security pre/post annotations ie @PreAuthorize and @PostAuthorize annotations
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

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
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests((authorize) -> authorize
                        .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .antMatchers("/api/auth/**").permitAll()
//                        .antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
//                        .antMatchers("/api/v1/auth/**").permitAll()
//                        .antMatchers("/v2/api-docs/**").permitAll()
//                        .antMatchers("/swagger-ui/**").permitAll()
//                        .antMatchers("/swagger-resources/**").permitAll()
//                        .antMatchers("/swagger-ui.html").permitAll()
//                        .antMatchers("/webjars/**").permitAll()
                        .anyRequest().authenticated() // All other paths must be authenticated.
                );
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
