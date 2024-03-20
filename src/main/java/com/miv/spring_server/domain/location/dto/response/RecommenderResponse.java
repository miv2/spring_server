package com.miv.spring_server.domain.location.dto.response;

import java.util.List;

public class RecommenderResponse {
    private String userName;
    private List<LocationResponseDto> location;

    public RecommenderResponse(String userName, List<LocationResponseDto> location) {
        this.userName = userName;
        this.location = location;
    }

    public String getUserName() {
        return userName;
    }

    public List<LocationResponseDto> getLocation() {
        return location;
    }
}
