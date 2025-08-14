package com.github.jnsbdev.todo.model;

import java.time.Instant;

public record TodoResponse(
        String id,
        String text,
        Instant createdAt
) {
}
