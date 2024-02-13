package com.miv.spring_server.domain.user.dto.response;

import com.miv.spring_server.domain.user.entity.User;

public class SignUpResponse {
    private String userName;
    private String email;

    public SignUpResponse(User user) {
        this.userName = user.getUserName();
        this.email = user.getEmail();
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }
}