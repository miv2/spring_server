package com.miv.spring_server.domain.location.service;

import com.miv.spring_server.domain.location.dto.request.LocationListRequestDto;
import com.miv.spring_server.domain.location.dto.response.LocationResponseDto;
import com.miv.spring_server.domain.location.dto.response.RecommenderLocationResponse;
import com.miv.spring_server.domain.location.entity.Location;
import com.miv.spring_server.domain.user.entity.User;

import java.util.List;

public interface LocationService {

    List<LocationResponseDto> getLocation(User user);

    void saveLocation(LocationListRequestDto locationRequestDto, User user);

    RecommenderLocationResponse recommenderLocation(String recommenderId);

    LocationResponseDto toResponse(Location location);

    List<LocationResponseDto> toResponse(List<Location> entities);

    List<LocationResponseDto> toLocationResponse(List<Location> entities);

}
