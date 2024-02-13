package com.miv.spring_server.domain.user.service;

import com.miv.spring_server.domain.user.entity.User;
import org.springframework.stereotype.Service;

public interface UserService {

    /**
     * User RefreshToken 저장
     * @param user
     * @param refreshToken
     */
    void saveRefreshToken(User user, String refreshToken);

    /**
     * 사용자 조회
     * @param uuid
     * @return
     */
    User findUserForJwt(String uuid);
}
