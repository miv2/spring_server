package com.miv.spring_server.auth.filter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miv.spring_server.common.handler.ApiError;
import com.miv.spring_server.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * unsuccessfulAuthentication 인증 실패 후 핸들러
 */
@Component
public class FailureHandler implements AuthenticationFailureHandler {

    /**
     * 인증 실패
     * @param request
     * @param response
     * @param exception
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.err.println("onAuthenticationFailure");

        ApiError apiError = convertException(exception);

        // 로그인 실패 이력
//        HistoryLogin failHistory = loginService.createFailHistory(username, apiError.getMessage());
//        loginService.saveHistory(failHistory);

        response.setStatus(apiError.getStatus());
        response.setContentType("application/json;charset=utf-8");

        ApiResponse apiResponse = new ApiResponse(apiError);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().println(objectMapper.writeValueAsString(apiResponse));
    }

    public static ApiError convertException(AuthenticationException exception) {
        String message = exception.getMessage();
        if(!ObjectUtils.isEmpty(message)) {
            //return new ApiError(HttpStatus.UNAUTHORIZED.value(),  "C000", message);
            return new ApiError(HttpStatus.OK.value(),  "C000", message);
        }

        if (exception instanceof BadCredentialsException) {
            return ApiError.BAD_CREDENTIALS_ERROR;
        } else if (exception instanceof UsernameNotFoundException) {
            return ApiError.USERNAME_NOT_FOUND_ERROR;
        } else if (exception instanceof AccountExpiredException) {
            return ApiError.ACCOUNT_EXPIRED_ERROR;
        } else if (exception instanceof CredentialsExpiredException) {
            return ApiError.ACCOUNT_CREDENTIAL_EXPIRED_ERROR;
        } else if (exception instanceof DisabledException) {
            return ApiError.ACCOUNT_DISABLED_ERROR;
        } else if (exception instanceof LockedException) {
            return ApiError.ACCOUNT_LOCKED_ERROR;
        } else if (exception instanceof InsufficientAuthenticationException) {
            return ApiError.ACCESS_DENIED_ERROR;
        } else {
            return ApiError.INTERNAL_SERVER_ERROR;
        }
    }

}
