package com.miv.spring_server.domain.location.service;

import com.miv.spring_server.domain.location.dto.request.LocationRequestDto;
import com.miv.spring_server.domain.location.dto.response.LocationResponseDto;
import com.miv.spring_server.domain.location.entity.Location;
import com.miv.spring_server.domain.user.entity.User;

import java.util.List;

public interface LocationService {

    List<LocationResponseDto> getLocation(User user);

    void saveLocation(LocationRequestDto locationRequestDto, User user);

    LocationResponseDto toResponse(Location location);

    List<LocationResponseDto> toResponse(List<Location> entities);

}
