package com.miv.spring_server.domain.location.dto.response;

import java.util.List;

public class LocationInfoResponseDto {
    private String name;
    private List<LocationResponseDto> locationList;

    public LocationInfoResponseDto(String name, List<LocationResponseDto> locationList) {
        this.name = name;
        this.locationList = locationList;
    }

    public String getName() {
        return name;
    }

    public List<LocationResponseDto> getLocationList() {
        return locationList;
    }
}
