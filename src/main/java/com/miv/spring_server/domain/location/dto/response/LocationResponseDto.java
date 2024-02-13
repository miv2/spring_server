package com.miv.spring_server.domain.location.dto.response;

public class LocationResponseDto {

    private double latitude;
    private double longitude;

    public LocationResponseDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
