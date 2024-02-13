package com.miv.spring_server.auth.jwt.extract;

public interface TokenExtractor {
    String extract(String header);
}
