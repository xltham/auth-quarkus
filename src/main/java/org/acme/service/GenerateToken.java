package org.acme.service;

import io.smallrye.jwt.build.Jwt;

import java.util.Set;


public class GenerateToken {
    public static String generateToken(String username, Set<String> roleName) {
        if (username == null || roleName == null) {
            throw new SecurityException("username and role cannot be null");
        }

        String token = Jwt.issuer("auth-quarkus")
                        .subject(username)
                        .groups(roleName)
                        .expiresIn(86400)// 24h
                        .sign();
        return token;
    }
}
