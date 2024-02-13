package com.miv.spring_server.domain.user.entity;

import com.miv.spring_server.domain.user.enums.RoleType;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Table(name = "USER_ROLE")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("권한")
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private RoleType role;

    public UserRole() {
    }

    public UserRole(RoleType role) {
        this.role = role;
    }

    public RoleType getRole() {
        return role;
    }
}
