package com.github.jnsbdev.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleUserExists(UserAlreadyExistsException ex) {
        return Map.of(
                "error", "USER_ALREADY_EXISTS",
                "username", ex.getUsername()
        );
    }

}
