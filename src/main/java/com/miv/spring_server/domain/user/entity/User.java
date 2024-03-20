package com.miv.spring_server.domain.user.entity;

import com.miv.spring_server.domain.user.enums.RoleType;
import org.hibernate.annotations.Comment;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "USER")
public class User {

    @Comment("일련번호")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("이름")
    @Column(name = "user_name")
    private String userName;

    @Comment("이메일")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Comment("패스워드")
    @Column(name = "password")
    private String password;

    @Comment("UUID")
    @Column(name = "uuid")
    private String uuid;

    @Comment("리프레쉬 토큰")
    @Column(name = "refresh_token", length = 500)
    private String refreshToken;

    @Comment("권한")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_id", nullable = false)
    private List<UserRole> roles;

    public User() { }

    public String getUserName() {
        return userName;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUuid() {
        return uuid;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void addRole(RoleType roleType) {
        UserRole role = new UserRole(roleType);
        if(ObjectUtils.isEmpty(roles)) {
            roles = List.of(role);
        } else {
            roles.add(role);
        }
    }

    public User setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", uuid='" + uuid + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", roles=" + roles +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String userName;
        private String email;
        private String password;
        private String uuid;
        private String refreshToken;

        private Builder() {
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public User build() {
            User user = new User();
            user.userName = this.userName;
            user.refreshToken = this.refreshToken;
            user.email = this.email;
            user.password = this.password;
            user.uuid = this.uuid;
            return user;
        }
    }
}
