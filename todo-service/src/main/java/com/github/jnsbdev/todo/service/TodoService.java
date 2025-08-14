package com.github.jnsbdev.todo.service;

import com.github.jnsbdev.todo.model.Todo;
import com.github.jnsbdev.todo.model.TodoRequest;
import com.github.jnsbdev.todo.model.TodoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final InMemoryTodoRepo repo;

    public TodoResponse save(String username, TodoRequest request) {
        Todo savedTodo = repo.save(username, request.text());
        return new TodoResponse(savedTodo.id(), savedTodo.text(), savedTodo.createdAt());
    }

    public List<TodoResponse> findAllByUsername(String username) {
        return repo.findAllByUser(username).stream()
                .map(todo -> new TodoResponse(todo.id(), todo.text(), todo.createdAt()))
                .toList();
    }
}
