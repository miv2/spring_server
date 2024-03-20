package com.miv.spring_server.domain.user.service;

import com.miv.spring_server.common.handler.ApiError;
import com.miv.spring_server.domain.user.dto.request.UserInfoRequest;
import com.miv.spring_server.domain.user.entity.User;
import com.miv.spring_server.domain.user.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserForJwt(String uuid) {
        return userRepository.findByUuid(uuid)
                .orElseThrow(() -> new JwtException(ApiError.ACCOUNT_NOT_FOUND_ERROR.getMessage()));
    }

    @Override
    public void saveRefreshToken(User user, String refreshToken) {
        user.setRefreshToken(refreshToken);
        userRepository.save(user);
    }

    @Override
    public void updateUserInfo(User user, UserInfoRequest userInfoRequest) {
        user.setUserInfo(userInfoRequest.getUserName(), userInfoRequest.getUserColor());
        userRepository.save(user);
    }
}
