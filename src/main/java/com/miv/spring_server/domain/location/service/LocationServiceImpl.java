package com.miv.spring_server.domain.location.service;

import com.miv.spring_server.domain.location.dto.request.LocationListRequestDto;
import com.miv.spring_server.domain.location.dto.request.LocationRequestDto;
import com.miv.spring_server.domain.location.dto.response.LocationResponseDto;
import com.miv.spring_server.domain.location.dto.response.RecommenderResponse;
import com.miv.spring_server.domain.location.entity.Location;
import com.miv.spring_server.domain.location.repository.LocationRepository;
import com.miv.spring_server.domain.user.entity.User;
import com.miv.spring_server.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocationServiceImpl implements LocationService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    public LocationServiceImpl(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public List<LocationResponseDto> getLocation(User user) {
        List<Location> locationInfo = locationRepository.findAllByUuid(user.getUuid());
        return toResponse(locationInfo, user.getUserName());
    }

    @Override
    public List<LocationResponseDto> recommenderLocation(String recommenderId) {
        List<Location> friendLocationList = locationRepository.findAllByUuidContains(recommenderId);
        Optional<User> recommenderUser = userRepository.findAllByUuidContains(recommenderId);

        String userName = recommenderUser.get().getUserName();

        return toResponse(friendLocationList, userName);
    }

    @Transactional
    @Override
    public void saveLocation(LocationListRequestDto locationRequestDto, User user) {

        List<LocationRequestDto> locationLists = locationRequestDto.getLocationList();

        for(LocationRequestDto locationList : locationLists) {
            Location location = new Location(
                    locationList.getLatitude(), locationList.getLongitude(),
                    LocalDateTime.now(), user.getUuid()
            );
            locationRepository.save(location);
        }
    }

    @Override
    public LocationResponseDto toResponse(Location location, String userName) {
        return new LocationResponseDto(location, userName);
    }

    @Override
    public List<LocationResponseDto> toResponse(List<Location> entities, String userName) {
        return entities.stream()
                .map(location -> toResponse(location, userName))
                .collect(Collectors.toList());
    }
}
