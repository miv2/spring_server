package com.miv.spring_server.auth.jwt;

import com.miv.spring_server.auth.principal.PrincipalDetails;
import com.miv.spring_server.auth.principal.Scopes;
import com.miv.spring_server.common.handler.ApiError;
import com.miv.spring_server.domain.user.entity.User;
import com.miv.spring_server.domain.user.service.UserService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    // Expire Time
    @Value("${spring.jwt.token-expiration-time}")
    private Integer tokenExpirationTime;

    // Token Issuer
    @Value("${spring.jwt.token-issuer}")
    private String tokenIssuer;

    // Token Sign Key
    @Value("${spring.jwt.token-signing-key}")
    private String tokenSigningKey;

    // Refresh Token Expire Time
    @Value("${spring.jwt.refresh-token-expiration-time}")
    private Integer refreshTokenExpirationTime;

    private UserService userService;

    public JwtTokenProvider(UserService userService) {
        this.userService = userService;
    }

    /**
     * JWT Access Token 발행 팩토리
     * @param principal
     * @return
     */
    public JwtToken createJwtAccessToken(PrincipalDetails principal) {
        String uuid = principal.getUsername();
        if (ObjectUtils.isEmpty(uuid)) {
            throw new UsernameNotFoundException(ApiError.USERNAME_NOT_FOUND_ERROR.getMessage());
        }

        if (ObjectUtils.isEmpty(principal.getAuthorities())) {
            throw new BadCredentialsException(ApiError.ACCOUNT_ROLES_EMPTY_ERROR.getMessage());
        }

        Claims claims = Jwts.claims().setSubject(uuid);
        List<String> scopes = principal.getAuthorities().stream()
                .map(m -> m.getAuthority())
                .collect(Collectors.toList());

        claims.put("scopes", scopes);

        // todo. accountToken 대칭키 암호화 예정
//        claims.put("data", new JwtClaimsData(""));

        LocalDateTime currentTime = LocalDateTime.now();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(tokenIssuer)
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(
                        Date.from(currentTime.plusMinutes(tokenExpirationTime)
                                .atZone(ZoneId.systemDefault()).toInstant())
                )
                .signWith(SignatureAlgorithm.HS512, tokenSigningKey)
                .compact();

        return new JwtToken(token, claims);
    }

    /**
     * JWT Refresh Access 토큰을 발행하기 위한 팩토리 메소드
     * @param principal
     * @return
     */
    public JwtToken createJwtRefreshToken(PrincipalDetails principal) {
        String username = principal.getUsername();
        if (ObjectUtils.isEmpty(username)) {
            throw new UsernameNotFoundException("Cannot create JWT Token without username");
        }

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));

        LocalDateTime currentTime = LocalDateTime.now();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(tokenIssuer)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(
                        Date.from(currentTime.plusMinutes(refreshTokenExpirationTime)
                                .atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, tokenSigningKey)
                .compact();
        return new JwtToken(token, claims);
    }

    /**
     * 만료된 JWT Access 토큰을 재발행
     * @param refreshJwtToken
     * @return
     */
    public JwtToken reIssueJwtAssessToken(JwtToken refreshJwtToken) {
        String refreshToken = refreshJwtToken.getToken();
        Jws<Claims> claims = getClaims(refreshToken);
        String uuid = claims.getBody().get("sub", String.class);

        // 갱신 토큰 검증
        User user = userService.findUserForJwt(uuid);

        PrincipalDetails principalDetails = new PrincipalDetails(user);

        return createJwtAccessToken(principalDetails);
    }

    /**
     * JWT Token 서명을 구문 분석하고 유효성 검증
     * @param token
     * @return
     */
    public Jws<Claims> getClaims(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(tokenSigningKey)
                    .parseClaimsJws(token);
            return claims;

        } catch (SignatureException ex) {
            throw new JwtException(ApiError.AUTHORIZATION_SIGNATURE_ERROR.getMessage());
        } catch (MalformedJwtException ex) {
            throw new JwtException(ApiError.AUTHORIZATION_MALFORMED_ERROR.getMessage());
        } catch (ExpiredJwtException ex) {
            throw new JwtException(ApiError.AUTHORIZATION_EXPIRED_ERROR.getMessage());
        } catch (UnsupportedJwtException ex) {
            throw new JwtException(ApiError.AUTHORIZATION_UNSUPPORTED_ERROR.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new JwtException(ApiError.UNAUTHORIZED_ERROR.getMessage());
        }
    }
}
