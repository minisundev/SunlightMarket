package com.raincloud.sunlightmarket.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raincloud.sunlightmarket.global.entity.UserRoleEnum;
import com.raincloud.sunlightmarket.global.jwt.JwtEntity;
import com.raincloud.sunlightmarket.global.jwt.JwtUtil;
import com.raincloud.sunlightmarket.global.jwt.TokenRepository;
import com.raincloud.sunlightmarket.user.dto.request.LoginRequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성 JwtAuthenticationFilter")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, TokenRepository tokenRepository) {
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;
        setFilterProcessesUrl("/api/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(),
                LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDto.getEmail(),
                    requestDto.getPassword(),
                    null
                )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authentication) {

        String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
        UserRoleEnum roleEnum = ((UserDetailsImpl) authentication.getPrincipal()).getUser()
            .getRole();

        String accessToken = jwtUtil.createAccessToken(username, roleEnum);
        String refreshToken = jwtUtil.createRefreshToken();

        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);

        JwtEntity jwt = JwtEntity.builder()
            .token(refreshToken.substring(7))
            .user(((UserDetailsImpl) authentication.getPrincipal()).getUser())
            .build();
        tokenRepository.save(jwt);


    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, AuthenticationException failed) {

        response.setStatus(401);
    }
}
