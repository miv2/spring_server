package com.miv.spring_server.auth.config;

import com.miv.spring_server.auth.filter.CustomAuthenticationProvider;
import com.miv.spring_server.auth.filter.JwtAuthorizationFilter;
import com.miv.spring_server.auth.filter.JwtExceptionFilter;
import com.miv.spring_server.auth.jwt.JwtTokenProvider;
import com.miv.spring_server.auth.jwt.extract.TokenExtractor;
import com.miv.spring_server.auth.jwt.verify.AccessTokenVerifier;
import com.miv.spring_server.common.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static com.miv.spring_server.auth.config.AuthenticationManagerConfig.authenticationManagerConfig;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private TokenExtractor tokenExtractor;
    private AccessTokenVerifier accessTokenVerifier;
    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(TokenExtractor tokenExtractor, AccessTokenVerifier accessTokenVerifier,
                          JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService,
                          BCryptPasswordEncoder passwordEncoder) {
        this.tokenExtractor = tokenExtractor;
        this.accessTokenVerifier = accessTokenVerifier;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .headers()
                .frameOptions().sameOrigin()
                .and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .cors().configurationSource(corsConfigurationSource())
                .and()

//                .addFilterBefore(new RequestFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthorizationFilter.class)
                .apply(authenticationManagerConfig(tokenExtractor, accessTokenVerifier, jwtTokenProvider))
                .and()

                .authorizeHttpRequests((authz) -> {
                    authz
                            .antMatchers(PermitPathMatcher.getPermitPaths()).permitAll()
                            .anyRequest().authenticated();
                });


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(customAuthenticationProvider());
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(),
                HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name()));
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");
        configuration.setExposedHeaders(List.of(Constants.AUTHENTICATION_HEADER_NAME,
                Constants.JWT_ACCESS_TOKEN_HEADER_NAME,
                Constants.JWT_REFRESH_TOKEN_HEADER_NAME));
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Origin", "*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .antMatchers("/css/**",
                        "/images/**",
                        "/js/**",
                        "/**/*.css",
                        "/**/*.js",
                        "/**/*.html",
                        "/static/**",
                        "/**/*.png",
                        "/**/*.jpg");
    }

}
