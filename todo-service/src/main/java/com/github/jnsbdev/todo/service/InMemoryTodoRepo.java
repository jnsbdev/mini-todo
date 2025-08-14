package com.github.jnsbdev.todo.service;

import com.github.jnsbdev.todo.model.Todo;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

@Repository
public class InMemoryTodoRepo {

    private final ConcurrentHashMap<String, ConcurrentLinkedDeque<Todo>> byUser = new ConcurrentHashMap<>();

    public Todo save(String username, String text) {
        Todo todoToSave = new Todo(java.util.UUID.randomUUID().toString(), text, Instant.now());
        byUser.computeIfAbsent(username, u -> new ConcurrentLinkedDeque<>())
                .addFirst(todoToSave);
        return todoToSave;
    }

    public List<Todo> findAllByUser(String username) {
        ConcurrentLinkedDeque<Todo> todos = byUser.get(username);
        if (todos == null) {
            return List.of();
        }
        return List.copyOf(todos);
    }

}
