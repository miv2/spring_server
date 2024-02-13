package com.miv.spring_server.auth.config;

import com.miv.spring_server.common.constants.URLConstants;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PermitPathMatcher {

    private OrRequestMatcher requestMatcher;
    private static final List<String> permitPath =
            Arrays.asList(
                    URLConstants.ROOT_URL,
                    URLConstants.API_AUTH_JOIN,
                    URLConstants.API_AUTH_LOGIN,
                    URLConstants.API_BOARD_URL,
                    URLConstants.SWAGGER_UI_URL,
                    URLConstants.SWAGGER_DOCS_URL
            );

    public PermitPathMatcher() {
        if(!permitPath.isEmpty()) {
            List<RequestMatcher> permitPathMatcher = permitPath.stream()
                    .map(AntPathRequestMatcher::new)
                    .collect(Collectors.toList());
            requestMatcher = new OrRequestMatcher(permitPathMatcher);
        }
    }

    public static String[] getPermitPaths() {
        return permitPath.toArray(new String[permitPath.size()]);
    }

    public boolean matches(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }
}
