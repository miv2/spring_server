package com.miv.spring_server.domain.location.entity;

import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOCATION")
public class Location {

    @Comment("일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("위도")
    @Column(name = "latitude")
    private double latitude;

    @Comment("경도")
    @Column(name = "longitude")
    private double longitude;

    @Comment("저장일시")
    @Column(name = "save_datetime")
    private LocalDateTime saveDateTime;

    @Comment("uuid")
    @Column(name = "uuid")
    private String uuid;

    public Location() {}

    public Location (double latitude, double longitude,
                    LocalDateTime saveDateTime, String uuid) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.saveDateTime = saveDateTime;
        this.uuid = uuid;
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

    public String getUuid() {
        return uuid;
    }
}
