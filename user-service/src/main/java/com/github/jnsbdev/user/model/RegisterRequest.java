package com.github.jnsbdev.user.model;

public record RegisterRequest(
        String username,
        String password
) {
}
