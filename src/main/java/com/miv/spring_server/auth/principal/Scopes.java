package com.miv.spring_server.auth.principal;

public enum Scopes {
    REFRESH_TOKEN;

    public String authority() {
        return "ROLE_" + name();
    }
}