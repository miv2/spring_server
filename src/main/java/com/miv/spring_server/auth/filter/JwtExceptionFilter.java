package com.miv.spring_server.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miv.spring_server.common.handler.ApiError;
import com.miv.spring_server.common.response.ApiResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    /**
     * Filter 내 Exception Handling
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);

        } catch (JwtException e) {
            e.printStackTrace();

            int httpStatusCode = HttpStatus.FORBIDDEN.value();
            ApiError apiError = new ApiError(httpStatusCode, "", e.getMessage());

            response.setStatus(httpStatusCode);
            response.setContentType("application/json;charset=utf-8");

            ApiResponse apiResponse = new ApiResponse(apiError);
            ObjectMapper objectMapper = new ObjectMapper();
            response.getWriter().println(objectMapper.writeValueAsString(apiResponse));

        }
    }
}
