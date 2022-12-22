/* (C)2022 */
package com.example.simpleblog.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired private JwtTokenProvider jwtTokenProvider;

    @Autowired private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // get token from http request
        String token = getTokenFromHeaders(request);

        // validate token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
            // get username from token
            String userName = jwtTokenProvider.getUsernameFromToken(token);

            // load user associated with token from db
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));

            // set user to spring security
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeaders(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length()); // "Bearer <accessToken>"
        }
        return null;
    }
}
