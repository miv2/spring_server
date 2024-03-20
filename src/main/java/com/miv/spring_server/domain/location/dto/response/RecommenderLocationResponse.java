package com.miv.spring_server.domain.location.dto.response;

import java.util.List;

public class RecommenderLocationResponse {
    private String userName;
    private String userColor;
    private List<LocationResponseDto> recommenderLocation;

    public RecommenderLocationResponse(String userName, String userColor, List<LocationResponseDto> location) {
        this.userName = userName;
        this.userColor = userColor;
        this.recommenderLocation = location;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserColor() {
        return userColor;
    }

    public List<LocationResponseDto> getRecommenderLocation() {
        return recommenderLocation;
    }
}
