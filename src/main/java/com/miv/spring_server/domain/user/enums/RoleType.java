package com.miv.spring_server.domain.user.enums;

public enum RoleType {
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private String name;

    RoleType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
