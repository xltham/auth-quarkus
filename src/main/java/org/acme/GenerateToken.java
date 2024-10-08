package org.acme;

import io.smallrye.jwt.build.Jwt;


public class JWTUtils {
    public static String generateToken(String username, String role) {
        if (username == null || role == null) {
            throw new IllegalArgumentException("username and role cannot be null");
        }

        String token = Jwt.issuer("auth-quarkus")
                        .subject(username)
                        .groups(role)
                        .expiresIn(86400)// 24h
                        .sign();
        return token;
    }
}
