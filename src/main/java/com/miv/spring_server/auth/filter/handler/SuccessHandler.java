package com.miv.spring_server.auth.filter.handler;

import com.miv.spring_server.auth.jwt.JwtToken;
import com.miv.spring_server.auth.jwt.JwtTokenProvider;
import com.miv.spring_server.auth.principal.PrincipalDetails;
import com.miv.spring_server.common.constants.Constants;
import com.miv.spring_server.domain.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인증 성공 핸들러
 * Authentication 객체를 SecurityContext 에 저장
 */
@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;

    public SuccessHandler(JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    /**
     * Header 에 jwt Token 할당
     * @param request
     * @param response
     * @param authentication
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.err.println("onAuthenticationSuccess");

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        JwtToken accessToken = jwtTokenProvider.createJwtAccessToken(principal);
        JwtToken refreshToken = jwtTokenProvider.createJwtRefreshToken(principal);

        // 발급받은 Refresh Token 저장
        userService.saveRefreshToken(principal.getUser(), refreshToken.getToken());

        response.setHeader(Constants.JWT_ACCESS_TOKEN_HEADER_NAME, accessToken.getToken());
        response.setHeader(Constants.JWT_REFRESH_TOKEN_HEADER_NAME, refreshToken.getToken());
    }

}
