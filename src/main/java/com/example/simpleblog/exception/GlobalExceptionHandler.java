/* (C)2022 */
package com.example.simpleblog.exception;

import com.example.simpleblog.dto.ErrorDetails;
import com.example.simpleblog.utils.AppConstants;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
// import org.springframework.web.context.request.WebRequest;

// import java.util.Date;

// @ControllerAdvice to handle exceptions globally
@ControllerAdvice
// ResponseEntityExceptionHandler to customise data validation response for Post and Put request
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // handle various specific exceptions
    // @ExceptionHandler(ResourceNotFoundException.class)
    // public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException
    // exception, WebRequest webRequest) {
    //     ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(),
    // webRequest.getDescription(false));
    //     return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    // }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException exception) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleBlogAPIException(BlogAPIException exception) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage());
        // return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(errorDetails, exception.getStatus());
    }

    // handles DTO validation for POST and PUT requests
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        exception
                .getBindingResult()
                .getAllErrors()
                .forEach(
                        (error) -> {
                            // cast error object to FieldError
                            String fieldName = ((FieldError) error).getField();
                            String fieldMessage = error.getDefaultMessage();
                            errors.put(fieldName, fieldMessage);
                        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // forbidden
    // unauthorized

    // handle global exceptions
    @ExceptionHandler(
            Exception.class) // all exceptions that extend Exception class will be handled here
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception) {
        System.out.println(">>>>>>>>");
        System.out.println(exception.getMessage());
        System.out.println(">>>>>>>>");
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Auth exceptions handlers

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDetails> handleAuthenticationException(
            AuthenticationException exception) {
        System.out.println("@ExceptionHandler >> AuthenticationException >>>>>>>>");
        System.out.println(exception.getMessage());
        System.out.println(">>>>>>>>");
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorDetails> handleBadCredentialsException(
            BadCredentialsException exception) {
        System.out.println("@ExceptionHandler >> BadCredentialsException >>>>>>>>");
        System.out.println(exception.getMessage());
        System.out.println(">>>>>>>>");
        ErrorDetails errorDetails = new ErrorDetails(AppConstants.BAD_CREDENTIALS_ERROR_MESSAGE);
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    //    @ExceptionHandler(AccessDeniedException.class)
    //    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException
    // exception) {
    //        System.out.println("@ExceptionHandler >> AccessDeniedException >>>>>>>>");
    //        System.out.println(exception.getMessage());
    //        System.out.println(">>>>>>>>");
    //        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage());
    //        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    //    }
}
