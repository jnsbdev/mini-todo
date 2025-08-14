package com.github.jnsbdev.todo.model;

import java.time.Instant;

public record Todo(
        String id,
        String text,
        Instant createdAt
) {}
