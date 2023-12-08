package com.raincloud.sunlightmarket.user.entity;

import com.raincloud.sunlightmarket.global.entity.Timestamped;
import com.raincloud.sunlightmarket.global.entity.UserRoleEnum;
import com.raincloud.sunlightmarket.global.jwt.JwtEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    Buyer buyer;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    Seller seller;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    JwtEntity jwtEntity;

    @Builder
    public User(final Long id, final Long kakaoId, final String nickname, final String password,
        final String email, final String intro, final UserRoleEnum role) {
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

    public void updatePassword(final String password) {
        this.password = password;
    }
}
