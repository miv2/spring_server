package com.miv.spring_server.domain.user.dto.response;

public class UserInfoResponse {
    private String name;
    private String email;
    private String uuid;

    public UserInfoResponse(String name, String email, String uuid) {
        this.name = name;
        this.email = email;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUuid() {
        return uuid;
    }

}
