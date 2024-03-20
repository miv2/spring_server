package com.miv.spring_server.domain.user.repository;

import com.miv.spring_server.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(@Param("email") String email);

    Optional<User> findByUuid(@Param("uuid") String uuid);

    User findByUuidContains(@Param("uuid") String uuid);

}
