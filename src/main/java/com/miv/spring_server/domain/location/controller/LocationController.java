package com.miv.spring_server.domain.location.controller;

import com.miv.spring_server.auth.security.SecurityService;
import com.miv.spring_server.common.response.ApiDataResponse;
import com.miv.spring_server.common.response.ApiResponse;
import com.miv.spring_server.domain.location.dto.request.LocationRequestDto;
import com.miv.spring_server.domain.location.dto.response.LocationResponseDto;
import com.miv.spring_server.domain.location.service.LocationService;
import com.miv.spring_server.domain.user.entity.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final SecurityService securityService;
    private final LocationService locationService;

    public LocationController(SecurityService securityService, LocationService locationService) {
        this.securityService = securityService;
        this.locationService = locationService;
    }

    @GetMapping
    public ApiResponse getLocation() {
        User user = securityService.findLoggedInUserPrincipal().getUser();
        return new ApiDataResponse<>(locationService.getLocation(user));
    }

    @PostMapping
    public ApiResponse updateLocation(@RequestBody LocationRequestDto locationRequestDto) {
        User user = securityService.findLoggedInUserPrincipal().getUser();
        locationService.updateLocation(locationRequestDto, user);
        return new ApiDataResponse<>("");
    }
}
