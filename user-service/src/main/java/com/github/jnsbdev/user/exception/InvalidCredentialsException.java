package com.github.jnsbdev.user.exception;

import lombok.Getter;

@Getter
public class InvalidCredentialsException extends RuntimeException {
    private final String username;
    public InvalidCredentialsException(String username) {
        super("Invalid user credentials");
        this.username = username;
    }
}
