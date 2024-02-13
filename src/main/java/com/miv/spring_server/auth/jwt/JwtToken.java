package com.miv.spring_server.auth.jwt;

import io.jsonwebtoken.Claims;

public class JwtToken {
    // JWT Token
    private String token;

    // Claims
    private Claims claims;

    public JwtToken(String token) {
        this.token = token;
    }

    public JwtToken(String token, Claims claims) {
        this.token = token;
        this.claims = claims;
    }

    public String getToken() {
        return token;
    }

    public Claims getClaims() {
        return claims;
    }
}
