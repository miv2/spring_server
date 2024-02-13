package com.miv.spring_server.domain.location.service;

import com.miv.spring_server.domain.location.dto.request.LocationRequestDto;
import com.miv.spring_server.domain.location.dto.response.LocationResponseDto;
import com.miv.spring_server.domain.location.entity.Location;
import com.miv.spring_server.domain.location.repository.LocationRepository;
import com.miv.spring_server.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public LocationResponseDto getLocation(User user) {
        Location locationInfo = locationRepository.findByUser(user);
        return new LocationResponseDto(locationInfo.getLatitude(), locationInfo.getLongitude());
    }

    @Override
    public void updateLocation(LocationRequestDto locationRequestDto, User user) {
        Location locationInfo = locationRepository.findByUser(user);

        if(!ObjectUtils.isEmpty(locationInfo)) {
            locationInfo.setLatitude(locationRequestDto.getLatitude());
            locationInfo.setLongitude(locationRequestDto.getLongitude());
            locationRepository.save(locationInfo);
        } else {
            Location savedLocation = new Location(locationRequestDto.getLatitude(), locationRequestDto.getLongitude(), user);
            locationRepository.save(savedLocation);
        }
    }

}
