package com.github.jnsbdev.user.service;

import com.github.jnsbdev.user.model.RegisterRequest;
import com.github.jnsbdev.user.model.RegisterResponse;
import com.github.jnsbdev.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final InMemoryUserRepo userRepo;

    //register
    public RegisterResponse register(RegisterRequest request) {
        User toSave = new User(request.username(), request.password());
        User saved = userRepo.save(toSave);
        return new RegisterResponse(saved.username());
    }

    //login

}
