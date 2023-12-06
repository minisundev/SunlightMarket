package com.raincloud.sunlightmarket.user.repository;

import com.raincloud.sunlightmarket.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);
    Optional<User> findByKakaoId(Long kakaoId);
}
