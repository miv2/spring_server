package com.miv.spring_server.auth.jwt.verify;

import com.miv.spring_server.auth.jwt.JwtTokenProvider;
import com.miv.spring_server.auth.principal.Scopes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class AccessTokenVerifier implements TokenVerifier {

    private JwtTokenProvider jwtTokenProvider;

    public AccessTokenVerifier(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * JWT Access Token 유효성 체크
     */
    @Override
    public boolean verify(String token) {

        Jws<Claims> claims = jwtTokenProvider.getClaims(token);
        if (ObjectUtils.isEmpty(claims)) {
            return false;
        }

        List<String> scopes = claims.getBody().get("scopes", List.class);

        if (scopes == null || scopes.isEmpty()
                || scopes.stream().filter(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope))
                .findFirst()
                .isPresent()) {

            return false;
        }

        return true;
    }
}
