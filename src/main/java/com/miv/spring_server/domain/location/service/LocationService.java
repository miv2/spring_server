package com.miv.spring_server.domain.location.service;

import com.miv.spring_server.domain.location.dto.request.LocationRequestDto;
import com.miv.spring_server.domain.location.dto.response.LocationResponseDto;
import com.miv.spring_server.domain.user.entity.User;

public interface LocationService {

    LocationResponseDto getLocation(User user);

    void updateLocation(LocationRequestDto locationRequestDto, User user);

}
