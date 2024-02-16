package com.miv.spring_server.domain.location.service;

import com.miv.spring_server.domain.location.dto.request.LocationRequestDto;
import com.miv.spring_server.domain.location.dto.response.LocationResponseDto;
import com.miv.spring_server.domain.location.entity.Location;
import com.miv.spring_server.domain.location.repository.LocationRepository;
import com.miv.spring_server.domain.user.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public List<LocationResponseDto> getLocation(User user) {
        List<Location> locationInfo = locationRepository.findAllByUuid(user.getUuid());
        return toResponse(locationInfo);
    }

    @Override
    public void saveLocation(LocationRequestDto locationRequestDto, User user) {

        Location location = new Location(
                locationRequestDto.getLatitude(), locationRequestDto.getLongitude(),
                LocalDateTime.now(), user.getUuid());

        locationRepository.save(location);
    }

    @Override
    public LocationResponseDto toResponse(Location location) {
        return new LocationResponseDto(location);
    }

    @Override
    public List<LocationResponseDto> toResponse(List<Location> entities) {
        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
