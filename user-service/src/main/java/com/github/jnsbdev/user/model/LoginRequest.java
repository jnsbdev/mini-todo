package com.github.jnsbdev.user.model;

public record LoginRequest(
        String username,
        String password
) {
}
