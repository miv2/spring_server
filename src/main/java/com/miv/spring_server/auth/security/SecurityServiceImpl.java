package com.miv.spring_server.auth.security;

import com.miv.spring_server.auth.principal.PrincipalDetails;
import com.miv.spring_server.common.handler.ApiError;
import com.miv.spring_server.common.handler.ApiException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class SecurityServiceImpl implements SecurityService {

    /**
     * 현재 로그인되어있는 사용자 Username 조회
     */
    @Override
    public String findLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            if(ObjectUtils.isEmpty(username)) {
                throw new ApiException(ApiError.PRINCIPAL_MISSING_ERROR, "");
            }
            return username;
        }else {
            throw new ApiException(ApiError.PRINCIPAL_MISSING_ERROR, "");
        }

    }

    /**
     * 현재 로그인되어있는 사용자 User 조회
     */
    @Override
    public PrincipalDetails findLoggedInUserPrincipal() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if(principal instanceof PrincipalDetails) {
            return (PrincipalDetails) principal;
        }else {
            throw new ApiException(ApiError.PRINCIPAL_MISSING_ERROR, "");
        }
    }
}
