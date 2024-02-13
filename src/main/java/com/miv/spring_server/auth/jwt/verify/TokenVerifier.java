package com.miv.spring_server.auth.jwt.verify;

/**
 * Token 검사
 */
public interface TokenVerifier {
    boolean verify(String token);
}
