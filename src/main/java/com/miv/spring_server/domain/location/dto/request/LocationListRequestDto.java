package com.miv.spring_server.domain.location.dto.request;

import java.util.List;

public class LocationListRequestDto {
    private List<LocationRequestDto> locationList;

    public List<LocationRequestDto> getLocationList() {
        return locationList;
    }
}
