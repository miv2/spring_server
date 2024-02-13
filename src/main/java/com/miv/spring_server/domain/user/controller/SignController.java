package com.miv.spring_server.domain.user.controller;

import com.miv.spring_server.auth.security.SecurityService;
import com.miv.spring_server.common.response.ApiDataResponse;
import com.miv.spring_server.common.response.ApiResponse;
import com.miv.spring_server.domain.user.dto.request.SignInRequest;
import com.miv.spring_server.domain.user.dto.request.SignUpRequest;
import com.miv.spring_server.domain.user.entity.User;
import com.miv.spring_server.domain.user.service.SignService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class SignController {
    private final SignService signService;
    private final SecurityService securityService;

    public SignController(SignService signService, SecurityService securityService) {
        this.signService = signService;
        this.securityService = securityService;
    }

    @PostMapping("/sign-up")
    public ApiResponse signUp(@RequestBody SignUpRequest request) {
        return new ApiDataResponse<>(signService.register(request));
    }

    @PostMapping("/sign-in")
    public ApiResponse signIn(SignInRequest request) {
        User user = securityService.findLoggedInUserPrincipal().getUser();
        return new ApiDataResponse<>(user.getUserName() + "님 환영합니다.");
    }

}
