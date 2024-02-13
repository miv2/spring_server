package com.miv.spring_server.auth.principal;

import com.miv.spring_server.common.handler.ApiError;
import com.miv.spring_server.domain.user.entity.User;
import com.miv.spring_server.domain.user.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public PrincipalDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = new User();

        try {
            user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException(ApiError.BAD_CREDENTIALS_ERROR.getMessage()));

        }catch (BadCredentialsException e) {
            e.printStackTrace();
        }

        return new PrincipalDetails(user);
    }
}
