package com.miv.spring_server.common.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import javax.naming.AuthenticationException;
import javax.naming.NamingException;

public class ApiError {

    /**
     * Client error messages - 4xx
     */
    public static final ApiError CLIENT_ERROR = new ApiError(HttpStatus.BAD_REQUEST.value(), "C000", "");
    public static final ApiError UNAUTHORIZED_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(), "C003", "인증 권한을 확인해 주세요.");
    public static final ApiError CREDENTIALS_NOT_FOUND_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(), "C004", "인증 토큰을 확인해 주세요.");
    public static final ApiError ACCESS_DENIED_ERROR = new ApiError(HttpStatus.FORBIDDEN.value(), "C005", "접근 권한을 확인해 주세요.");
    public static final ApiError DATA_NOT_FOUND_ERROR = new ApiError(HttpStatus.BAD_REQUEST.value(),"C007", "데이터를 찾을 수 없어요");
    /**
     * Server error messages - 5xx
     */
    public static final ApiError SERVER_ERROR = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"S000", "");
    public static final ApiError INTERNAL_SERVER_ERROR = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"S001", "일시적 오류로 서비스 처리가 지연되고 있어요.");
    public static final ApiError BAD_CREDENTIALS_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S002", "계정 자격 증명에 실패했습니다.");
    public static final ApiError USERNAME_NOT_FOUND_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S003", "계정 정보를 확인해 주세요.");
    public static final ApiError ACCOUNT_NOT_FOUND_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S004", "계정 정보를 확인해 주세요.");
    public static final ApiError ACCOUNT_ROLES_EMPTY_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S005", "계정 권한이 유효하지 않습니다.");
    public static final ApiError ACCOUNT_EXPIRED_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S006", "요청 처리 중 오류가 발생 했습니다.");
    public static final ApiError ACCOUNT_CREDENTIAL_EXPIRED_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S007", "계정이 만료되었습니다.");
    public static final ApiError ACCOUNT_DISABLED_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S008", "계정이 비활성화 되었습니다.");
    public static final ApiError ACCOUNT_LOCKED_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S009", "계정을 사용할 수 없습니다.");
    public static final ApiError ACCOUNT_LEAVE_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S010", "계정을 사용할 수 없습니다.");
    public static final ApiError AUTHORIZATION_SIGNATURE_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S011", "인증 토큰을 확인해 주세요.");
    public static final ApiError AUTHORIZATION_MALFORMED_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S012", "인증 토큰을 확인해 주세요.");
    public static final ApiError AUTHORIZATION_EXPIRED_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S013", "유효시간이 만료되었습니다. 재서명 해주세요.");
    public static final ApiError AUTHORIZATION_UNSUPPORTED_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S014", "인증 토큰을 확인해 주세요.");
    public static final ApiError AUTHORIZATION_TOKEN_REISSUED_ERROR = new ApiError(HttpStatus.UNAUTHORIZED.value(),"S015", "재발급 토큰이 올바르지 않습니다.");
    public static final ApiError PRINCIPAL_MISSING_ERROR = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),"S016", "계정정보가 유실되었습니다. 재로그인 해주세요.");

    private int status;
    private String code;
    private String message;

    public ApiError(String code, String message) {
        this(HttpStatus.INTERNAL_SERVER_ERROR.value(), code, message);
    }

    public ApiError(int status, String code, String message) {
        super();
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public static ApiError convert(AuthenticationException exception) {
        if (exception instanceof NamingException) {
            return ApiError.USERNAME_NOT_FOUND_ERROR;
        } else {
            return ApiError.SERVER_ERROR;
        }
    }
}
