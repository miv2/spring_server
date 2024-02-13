package com.miv.spring_server.common.handler;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int status;
    private String code;
    private String message;

    public ApiException(ApiError apiError, String message) {
        super(apiError.getMessage());
        this.status = apiError.getStatus();
        this.code = apiError.getCode();
        this.message = apiError.getMessage() + message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
