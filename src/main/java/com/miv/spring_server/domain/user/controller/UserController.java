package com.miv.spring_server.domain.user.controller;

import com.miv.spring_server.auth.security.SecurityService;
import com.miv.spring_server.common.response.ApiDataResponse;
import com.miv.spring_server.common.response.ApiResponse;
import com.miv.spring_server.domain.user.dto.response.UserInfoResponse;
import com.miv.spring_server.domain.user.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class UserController {

    private final SecurityService securityService;

    public UserController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    public ApiResponse userInfo() {
        User user = securityService.findLoggedInUserPrincipal().getUser();
        return new ApiDataResponse<>(new UserInfoResponse(user.getUserName(), user.getEmail()));
    }

}
