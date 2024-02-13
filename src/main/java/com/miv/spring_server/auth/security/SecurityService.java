package com.miv.spring_server.auth.security;

import com.miv.spring_server.auth.principal.PrincipalDetails;

public interface SecurityService {

    /**
     * 현재 로그인되어있는 사용자 Username 조회
     * @return
     */
    String findLoggedInUsername();

    /**
     * 현재 로그인되어있는 사용자 User 조회
     * @return
     */
    PrincipalDetails findLoggedInUserPrincipal();
}
