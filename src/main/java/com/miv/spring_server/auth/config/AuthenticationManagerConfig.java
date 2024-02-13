package com.miv.spring_server.auth.config;

import com.miv.spring_server.auth.filter.JwtAuthorizationFilter;
import com.miv.spring_server.auth.filter.LoginAuthenticationFilter;
import com.miv.spring_server.auth.filter.handler.FailureHandler;
import com.miv.spring_server.auth.filter.handler.SuccessHandler;
import com.miv.spring_server.auth.jwt.JwtTokenProvider;
import com.miv.spring_server.auth.jwt.extract.TokenExtractor;
import com.miv.spring_server.auth.jwt.verify.AccessTokenVerifier;
import com.miv.spring_server.common.constants.URLConstants;
import com.miv.spring_server.common.utils.BeanUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class AuthenticationManagerConfig extends AbstractHttpConfigurer<AuthenticationManagerConfig, HttpSecurity> {

    private TokenExtractor tokenExtractor;
    private AccessTokenVerifier accessTokenVerifier;
    private JwtTokenProvider jwtTokenProvider;

    public AuthenticationManagerConfig(TokenExtractor tokenExtractor, AccessTokenVerifier accessTokenVerifier, JwtTokenProvider jwtTokenProvider) {
        this.tokenExtractor = tokenExtractor;
        this.accessTokenVerifier = accessTokenVerifier;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public static AuthenticationManagerConfig authenticationManagerConfig(TokenExtractor tokenExtractor, AccessTokenVerifier accessTokenVerifier, JwtTokenProvider jwtTokenProvider) {
        return new AuthenticationManagerConfig(tokenExtractor, accessTokenVerifier, jwtTokenProvider);
    }

    @Override
    public void configure(HttpSecurity http) {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilter(loginAuthenticationFilter(authenticationManager));
        http.addFilter(jwtAuthorizationFilter(authenticationManager));
    }

    /**
     * 로그인 요청 필터
     * @param authenticationManager
     * @return
     */
    public LoginAuthenticationFilter loginAuthenticationFilter(AuthenticationManager authenticationManager) {
        LoginAuthenticationFilter filter = new LoginAuthenticationFilter(authenticationManager);
        AuthenticationSuccessHandler successHandlerBean = (AuthenticationSuccessHandler) BeanUtils.getBean(SuccessHandler.class);
        AuthenticationFailureHandler failureHandlerBean = (AuthenticationFailureHandler) BeanUtils.getBean(FailureHandler.class);

        filter.setFilterProcessesUrl(loginRequestPathMatcher().getPattern());
        filter.setAuthenticationSuccessHandler(successHandlerBean);
        filter.setAuthenticationFailureHandler(failureHandlerBean);
        return filter;
    }

    /**
     * 로그인 요청을 받을 URI, HttpMethod-POST
     * @return
     */
    public AntPathRequestMatcher loginRequestPathMatcher() {
        return new AntPathRequestMatcher(URLConstants.API_AUTH_LOGIN, HttpMethod.POST.name());
    }

    /**
     * JWT 인가 요청 필터
     * @param authenticationManager
     * @return
     */
    public JwtAuthorizationFilter jwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        JwtAuthorizationFilter filter = new JwtAuthorizationFilter(authenticationManager, tokenExtractor, accessTokenVerifier, jwtTokenProvider);
        return filter;
    }

}
