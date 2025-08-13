package com.github.jnsbdev.user.service;

import com.github.jnsbdev.user.exception.InvalidCredentialsException;
import com.github.jnsbdev.user.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final InMemoryUserRepo userRepo;
    private final JwtService jwtService;

    //register
    public RegisterResponse register(RegisterRequest request) {
        User toSave = new User(request.username(), request.password());
        User saved = userRepo.save(toSave);
        return new RegisterResponse(saved.username());
    }

    //login
    public LoginResponse login(LoginRequest request) {
        User user = userRepo.findByUsername(request.username())
                .orElseThrow(() -> new InvalidCredentialsException(request.username()));

        if (!user.password().equals(request.password())) {
            throw new InvalidCredentialsException(request.username());
        }

        String token = jwtService.issue(user.username());
        return new LoginResponse(token);
    }
}
