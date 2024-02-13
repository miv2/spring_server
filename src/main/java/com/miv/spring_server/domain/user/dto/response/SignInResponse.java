package com.miv.spring_server.domain.user.dto.response;

public class SignInResponse {

    private String email;

    public SignInResponse(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
