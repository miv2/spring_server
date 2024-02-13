package com.miv.spring_server.domain.location.repository;

import com.miv.spring_server.domain.location.entity.Location;
import com.miv.spring_server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByUser(@Param("user") User user);
}
