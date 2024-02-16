package com.miv.spring_server.domain.location.repository;

import com.miv.spring_server.domain.location.entity.Location;
import com.miv.spring_server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {
    List<Location> findAllByUuid(@Param("uuid") String uuid);
}
