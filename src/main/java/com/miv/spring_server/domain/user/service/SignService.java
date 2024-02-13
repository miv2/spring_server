package com.miv.spring_server.domain.user.service;

import com.miv.spring_server.auth.jwt.JwtTokenProvider;
import com.miv.spring_server.common.handler.ApiError;
import com.miv.spring_server.common.handler.ApiException;
import com.miv.spring_server.domain.user.dto.request.SignInRequest;
import com.miv.spring_server.domain.user.dto.request.SignUpRequest;
import com.miv.spring_server.domain.user.dto.response.SignInResponse;
import com.miv.spring_server.domain.user.dto.response.SignUpResponse;
import com.miv.spring_server.domain.user.entity.User;
import com.miv.spring_server.domain.user.enums.RoleType;
import com.miv.spring_server.domain.user.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class SignService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwtTokenProvider;

    public SignService(UserRepository userRepository, PasswordEncoder encoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public SignUpResponse register(SignUpRequest request) {

        User user = User.builder()
                .uuid(UUID.randomUUID().toString())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .userName(request.getUserName())
                .build();

        user.addRole(RoleType.ROLE_USER);

        userRepository.save(user);

        return new SignUpResponse(user);
    }

    public void signIn() {

//        User user = userRepository.findByEmail(request.getEmail())
//                .filter(it -> encoder.matches(request.getPassword(), it.getPassword()))
//                .orElseThrow(() -> new ApiException(ApiError.SERVER_ERROR, "아이디 또는 비밀번호가 일치하지않습니다."));
//        return new SignInResponse(user.getEmail());
    }

}
