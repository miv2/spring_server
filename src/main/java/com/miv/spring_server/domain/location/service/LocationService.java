package com.miv.spring_server.domain.location.service;

import com.miv.spring_server.domain.location.dto.request.LocationListRequestDto;
import com.miv.spring_server.domain.location.dto.request.LocationRequestDto;
import com.miv.spring_server.domain.location.dto.response.LocationResponseDto;
import com.miv.spring_server.domain.location.dto.response.RecommenderResponse;
import com.miv.spring_server.domain.location.entity.Location;
import com.miv.spring_server.domain.user.entity.User;

import java.util.List;

public interface LocationService {

    // 내 위치 가져오기
    List<LocationResponseDto> getLocation(User user);

    // 내 위치 저장
    void saveLocation(LocationListRequestDto locationRequestDto, User user);

    // 친구 위치 보기
    List<LocationResponseDto> recommenderLocation(String recommenderId);

    LocationResponseDto toResponse(Location location, String userName);

    List<LocationResponseDto> toResponse(List<Location> entities, String userName);

}
