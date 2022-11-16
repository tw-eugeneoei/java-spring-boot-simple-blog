package com.example.simpleblog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

// @ResponseStatus annotation cause speing boot to respond with the specified HTTP status code whenever this exception
// is thrown from the controller
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private UUID fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, UUID fieldValue) {
        super(String.format("%s not found with %s: '%s'", resourceName, fieldName, fieldValue)); // eg: Post not found with id: 1
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getResourceName() {
        return resourceName;
    }

    public UUID getFieldValue() {
        return fieldValue;
    }
}
