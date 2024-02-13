package com.miv.spring_server.domain.user.dto.response;

public class UserInfoResponse {
    private String name;
    private String email;

    public UserInfoResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
