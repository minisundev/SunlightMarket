package com.raincloud.sunlightmarket.global.security;

import static com.raincloud.sunlightmarket.global.jwt.JwtUtil.ACCESS_TOKEN_HEADER;
import static com.raincloud.sunlightmarket.global.jwt.JwtUtil.BEARER_PREFIX;
import static com.raincloud.sunlightmarket.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;

import com.raincloud.sunlightmarket.global.jwt.JwtUtil;
import com.raincloud.sunlightmarket.global.jwt.TokenRepository;
import com.raincloud.sunlightmarket.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, TokenRepository tokenRepository,
        UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.tokenRepository = tokenRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws ServletException, IOException {

        String accessToken = jwtUtil.getJwtFromHeader(request, ACCESS_TOKEN_HEADER);

        if (StringUtils.hasText(accessToken) && !jwtUtil.validateToken(accessToken)) {
            String refreshToken = jwtUtil.getJwtFromHeader(request, REFRESH_TOKEN_HEADER);

            if (StringUtils.hasText(accessToken) && !jwtUtil.validateToken(refreshToken)) {
                User user = tokenRepository.findByToken(refreshToken);
                accessToken = jwtUtil.createAccessToken(user.getEmail(), user.getRole()
                ).substring(7);
                response.addHeader("AccessToken", BEARER_PREFIX + accessToken);
            }
        }

        if (StringUtils.hasText(accessToken)) {
            Claims info = jwtUtil.getUserInfoFromToken(accessToken);
            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }

        chain.doFilter(request, response);
    }

    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }
}
