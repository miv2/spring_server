package com.miv.spring_server.auth.filter;

import com.miv.spring_server.auth.config.PermitPathMatcher;
import com.miv.spring_server.auth.jwt.JwtTokenProvider;
import com.miv.spring_server.auth.jwt.extract.TokenExtractor;
import com.miv.spring_server.auth.jwt.verify.AccessTokenVerifier;
import com.miv.spring_server.auth.principal.PrincipalDetails;
import com.miv.spring_server.common.constants.Constants;
import com.miv.spring_server.common.handler.ApiError;
import com.miv.spring_server.common.handler.ApiException;
import com.miv.spring_server.common.utils.BeanUtils;
import com.miv.spring_server.domain.user.entity.User;
import com.miv.spring_server.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private TokenExtractor tokenExtractor;
    private AccessTokenVerifier accessTokenVerifier;
    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, TokenExtractor tokenExtractor, AccessTokenVerifier accessTokenVerifier, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.tokenExtractor = tokenExtractor;
        this.accessTokenVerifier = accessTokenVerifier;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * JWT 인가
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenPayload = request.getHeader(Constants.AUTHENTICATION_HEADER_NAME);

        // permit 경로 일 경우 토큰이 존재하면, 시큐리티 컨텍스트에 저장 처리
        PermitPathMatcher permitPathMatcher = new PermitPathMatcher();
        if(permitPathMatcher.matches(request)) {
            if(!ObjectUtils.isEmpty(tokenPayload)) {
                authenticateToken(tokenPayload);
            }else {
                chain.doFilter(request, response);
                return ;
            }
        }

        authenticateToken(tokenPayload);
        chain.doFilter(request, response);
    }

    private void authenticateToken(String tokenPayload) {
        String token = tokenExtractor.extract(tokenPayload);

        if(!accessTokenVerifier.verify(token)) {
            throw new ApiException(ApiError.UNAUTHORIZED_ERROR, "");
        }

        Jws<Claims> claims = jwtTokenProvider.getClaims(token);
        String uuid = claims.getBody().getSubject();

        UserService userService = (UserService) BeanUtils.getBean(UserService.class);
        User user = userService.findUserForJwt(uuid);

        PrincipalDetails principalDetails = new PrincipalDetails(user);

        if(!principalDetails.isAccountNonLocked()) {
            throw new ApiException(ApiError.ACCOUNT_LEAVE_ERROR, "");
        }

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}