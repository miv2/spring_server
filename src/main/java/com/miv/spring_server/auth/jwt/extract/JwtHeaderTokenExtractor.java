package com.miv.spring_server.auth.jwt.extract;

import com.miv.spring_server.common.handler.ApiError;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * 토큰 추출
 * Jwt Token Extract
 */
@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {

    public static String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(String header) {

        if (ObjectUtils.isEmpty(header) || !header.startsWith(HEADER_PREFIX) || header.length() < HEADER_PREFIX.length()) {
            throw new JwtException(ApiError.CREDENTIALS_NOT_FOUND_ERROR.getMessage());
        }
        return header.substring(HEADER_PREFIX.length());
    }
}
