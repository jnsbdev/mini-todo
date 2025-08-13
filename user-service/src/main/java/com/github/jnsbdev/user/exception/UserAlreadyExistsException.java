package com.github.jnsbdev.user.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {
    private final String username;

    public UserAlreadyExistsException(String username) {
        super("User already exists: " + username);
        this.username = username;
    }

}
