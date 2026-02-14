package com.secureauth.secure_access_api.main.infra.exception.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.secureauth.secure_access_api.main.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class tokenService {
    @Value("${api.security.token.secret}")
    private String secret;
    public String generateToken(User user){
        try {
            var algoritmo = Algorithm.HMAC256(secret);
             return JWT.create()
                    .withIssuer("API Security GDS")
                     .withSubject(user.getLogin())
                     .withExpiresAt(dataExpiration())
                    .sign(algoritmo);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error to generat JWT Token");
        }
    }

    private Instant dataExpiration() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
