package com.miv.spring_server.domain.location.entity;

import com.miv.spring_server.domain.user.entity.User;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

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

    @Comment("유저")
    @JoinColumn(name = "user_id")
    @OneToOne
    private User user;

    public Location() {}

    public Location(double latitude, double longitude, User user) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


}
