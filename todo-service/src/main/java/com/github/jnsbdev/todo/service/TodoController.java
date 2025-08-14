package com.github.jnsbdev.todo.service;

import com.github.jnsbdev.todo.model.TodoRequest;
import com.github.jnsbdev.todo.model.TodoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse save(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody TodoRequest request
    ) {
        return todoService.save(jwt.getSubject(), request);
    }

    @GetMapping
    public List<TodoResponse> findAllByUsername(
            @AuthenticationPrincipal Jwt jwt
    ) {
        return todoService.findAllByUsername(jwt.getSubject());
    }

}
