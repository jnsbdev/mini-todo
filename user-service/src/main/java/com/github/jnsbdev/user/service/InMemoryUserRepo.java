package com.github.jnsbdev.user.service;

import com.github.jnsbdev.user.exception.UserAlreadyExistsException;
import com.github.jnsbdev.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepo {

    private final ConcurrentHashMap<String, User> usersByUsername = new ConcurrentHashMap<>();

    public User save(User toSave) {
        final String username = toSave.username();
        User existing = usersByUsername.putIfAbsent(username, toSave);
        if (existing != null) {
            throw new UserAlreadyExistsException(username);
        }
        return toSave;
    }

    public java.util.Optional<User> findByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username));
    }
}
