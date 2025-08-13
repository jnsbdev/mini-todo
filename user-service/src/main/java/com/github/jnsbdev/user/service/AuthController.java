package com.github.jnsbdev.user.service;

import com.github.jnsbdev.user.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    //register
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    //login
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    //token
    @PostMapping("/token")
    public ValidateResponse validate(@RequestBody ValidateRequest request) {
        boolean isValid = jwtService.isValid(request.token());
        return new ValidateResponse(isValid);
    }
}
