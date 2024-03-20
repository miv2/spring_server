package com.miv.spring_server.domain.location.dto.response;

import com.miv.spring_server.domain.location.entity.Location;

import java.time.LocalDateTime;

public class LocationResponseDto {
    private Long id;
    private double latitude;
    private double longitude;
    private LocalDateTime saveDateTime;

    public LocationResponseDto(Location location) {
        this.id = location.getId();
        this.latitude = location.getLatitude();
        this.longitude = location.getLongitude();
        this.saveDateTime = location.getSaveDateTime();
    }

    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public LocalDateTime getSaveDateTime() {
        return saveDateTime;
    }

}
