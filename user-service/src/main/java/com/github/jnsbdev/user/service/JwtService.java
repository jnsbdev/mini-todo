package com.github.jnsbdev.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    @Value("${jwt.ttl}")
    private java.time.Duration ttl;

    public String issue (String subject) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(subject)
                .issuer("user-service")
                .audience(List.of("todo-service"))
                .issuedAt(now)
                .expiresAt(now.plus(ttl))
                .build();

        JwsHeader header = JwsHeader
                .with(MacAlgorithm.HS256)
                .build();

        return encoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }

    public boolean isValid(String token) {
        if (token == null || token.isBlank()) return false;
        try {
            decoder.decode(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
