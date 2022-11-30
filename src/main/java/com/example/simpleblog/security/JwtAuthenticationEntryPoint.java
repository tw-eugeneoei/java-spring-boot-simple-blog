package com.example.simpleblog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    // this method is called whenever an exception is thrown due to an unauthorised/unauthenticated user trying to access a resource that requires authentication
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // this follows tutorial but exception message does not return
        // AccessDeniedException is thrown and tried to catch in GlobalExceptionHandler but no match was found
        // response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());

        // this throws AuthenticationException
        resolver.resolveException(request, response, null, authException);
    }
}
