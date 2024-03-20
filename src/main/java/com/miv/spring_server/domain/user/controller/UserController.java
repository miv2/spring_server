package com.miv.spring_server.domain.user.controller;

import com.miv.spring_server.auth.security.SecurityService;
import com.miv.spring_server.common.response.ApiDataResponse;
import com.miv.spring_server.common.response.ApiResponse;
import com.miv.spring_server.domain.user.dto.request.UserInfoRequest;
import com.miv.spring_server.domain.user.dto.response.UserInfoResponse;
import com.miv.spring_server.domain.user.entity.User;
import com.miv.spring_server.domain.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member")
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping
    public ApiResponse userInfo() {
        User user = securityService.findLoggedInUserPrincipal().getUser();

        String uuid = user.getUuid(); // 203aa63a-392e-484a-8ed1-4e749c087bea
        int indexOf = uuid.indexOf("-"); // 8
        String recommenderCode = uuid.substring(0, indexOf);

        // System.out.println(recommenderCode);

        return new ApiDataResponse<>(
                new UserInfoResponse(user.getUserName(), user.getEmail(),
                user.getUuid(), recommenderCode, user.getColor())
        );
    }

    @PostMapping
    public ApiResponse modifyUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        User user = securityService.findLoggedInUserPrincipal().getUser();
        userService.updateUserInfo(user, userInfoRequest);
        return new ApiDataResponse<>("닉네임 변경완료");
    }

}
