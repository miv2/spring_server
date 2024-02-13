package com.miv.spring_server.auth.jwt;

public class JwtClaimsData {
    private String secret;

    public JwtClaimsData(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }
}
