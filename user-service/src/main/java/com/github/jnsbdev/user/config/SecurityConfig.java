package com.github.jnsbdev.user.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/register", "/login", "/token").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    SecretKey jwtSecretKey(@Value("${jwt.secret}") String key) {
        byte[] secret = Base64.getDecoder().decode(key);
        return new SecretKeySpec(secret, "HmacSHA256");
    }

    @Bean
    JwtEncoder jwtEncoder(SecretKey key) {
        var source = new ImmutableSecret<>(key);
        return new NimbusJwtEncoder(source);
    }

    @Bean
    JwtDecoder jwtDecoder(SecretKey key) {
        var decoder = NimbusJwtDecoder.withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();

        var withIssuer = JwtValidators.createDefaultWithIssuer("user-service");

        OAuth2TokenValidator<Jwt> audience = jwt -> {
            var aud = jwt.getAudience();
            if (aud != null && aud.contains("todo-service")) {
                return OAuth2TokenValidatorResult.success();
            }
            return OAuth2TokenValidatorResult.failure(
                    new OAuth2Error("invalid_token", "aud mismatch", null)
            );
        };

        OAuth2TokenValidator<Jwt> subject = jwt -> {
            String sub = jwt.getSubject();
            if (sub != null && !sub.isBlank()) {
                return OAuth2TokenValidatorResult.success();
            }
            return OAuth2TokenValidatorResult.failure(
                    new OAuth2Error("invalid_token", "missing sub", null)
            );
        };

        decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(withIssuer, audience, subject));
        return decoder;
    }
}
