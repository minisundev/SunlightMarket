package com.raincloud.sunlightmarket.global.jwt;

import com.raincloud.sunlightmarket.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<JwtEntity,Long> {

    User findByToken(String refreshToken);
    JwtEntity findByUserId(Long userId);
}
