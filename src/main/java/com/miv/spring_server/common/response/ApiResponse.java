package com.miv.spring_server.common.response;

import com.miv.spring_server.common.constants.Constants;
import com.miv.spring_server.common.handler.ApiError;
import org.springframework.http.HttpStatus;

public class ApiResponse {
    //공통적으로 갖고 있는것
    private String status;
    private ApiError error;

    public ApiResponse() {
        status = Constants.HTTP_RESPONSE_STATUS_OK;
        error = new ApiError(HttpStatus.OK.value(),"", "");
    }

    public ApiResponse(ApiError error) {
        status = Constants.HTTP_RESPONSE_STATUS_FAIL;
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public ApiError getError() {
        return error;
    }
}
