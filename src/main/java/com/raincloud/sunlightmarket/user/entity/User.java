package com.raincloud.sunlightmarket.user.entity;

import com.raincloud.sunlightmarket.global.entity.Timestamped;
import com.raincloud.sunlightmarket.global.entity.UserRoleEnum;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "User")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long kakao_id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String intro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoleEnum role;

    @Builder
    public User(final Long id, final Long kakao_id, final String username, final String password, final String email, final String intro, final UserRoleEnum role) {
        this.id = id;
        this.kakao_id = kakao_id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.intro = intro;
        this.role = role;
    }

    public User kakaoIdUpdate(Long kakao_id) {
        this.kakao_id = kakao_id;
        return this;
    }
}
