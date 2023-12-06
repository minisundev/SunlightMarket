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

    @Column
    private Long kakaoId;

    @Column(nullable = false)
    private String nickname;

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
    public User(final Long id, final Long kakaoId, final String nickname, final String password, final String email, final String intro, final UserRoleEnum role) {
        this.id = id;
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.intro = intro;
        this.role = role;
    }

    public User(Long kakaoId, String nickname, String password, String email, UserRoleEnum role) {
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void updateProfile(String nickname, String intro) {
        this.nickname = nickname;
        this.intro = intro;
    }
}
