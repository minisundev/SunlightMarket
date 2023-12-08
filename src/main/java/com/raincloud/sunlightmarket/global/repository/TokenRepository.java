package com.raincloud.sunlightmarket.global.repository;

import com.raincloud.sunlightmarket.global.jwt.JwtUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<JwtUtil,Long> {

}
