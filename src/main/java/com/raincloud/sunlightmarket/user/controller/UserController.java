package com.raincloud.sunlightmarket.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.raincloud.sunlightmarket.global.dto.ApiResponse;
import com.raincloud.sunlightmarket.global.jwt.JwtUtil;
import com.raincloud.sunlightmarket.global.security.UserDetailsImpl;
import com.raincloud.sunlightmarket.user.dto.request.MyProfileRequestDto;
import com.raincloud.sunlightmarket.user.dto.request.PasswordRequestDto;
import com.raincloud.sunlightmarket.user.dto.request.SingUpRequestDto;
import com.raincloud.sunlightmarket.user.dto.response.MyProfileResponseDto;
import com.raincloud.sunlightmarket.user.dto.response.SignUpResponseDto;
import com.raincloud.sunlightmarket.user.dto.response.UserProfileResponseDto;
import com.raincloud.sunlightmarket.user.service.KakaoService;
import com.raincloud.sunlightmarket.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    @PostMapping("/signup")
    public ApiResponse<SignUpResponseDto> signUp(@RequestBody SingUpRequestDto requestDto) {
        System.out.println("controller");
        SignUpResponseDto responseDto = userService.signUp(requestDto);
        return new ApiResponse<>(HttpStatus.CREATED.value(),"회원가입 성공",responseDto);
    }

    @GetMapping("/kakao/callback")
    public ApiResponse<Void> kakaoLogin(@RequestBody String code, HttpServletResponse response) throws JsonProcessingException {
            String token = kakaoService.kakaoLogin(code);

            Cookie cookie = new Cookie(JwtUtil.ACCESS_TOKEN_HEADER, token.substring(7));
            response.addCookie(cookie);
            return new ApiResponse<>(HttpStatus.OK.value(),"카카오 로그인 성공");
        }

        @PutMapping("/profile")
        public ApiResponse<MyProfileResponseDto> updateProfile(
                @RequestBody MyProfileRequestDto requestDto,
                @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
            MyProfileResponseDto responseDto = userService.updateProfile(userDetails.getUser(), requestDto);
            return new ApiResponse<>(HttpStatus.OK.value(),"프로필 업데이트 성공",responseDto);
        }
        
        @GetMapping("/profile")
        public ApiResponse<MyProfileResponseDto> myProfile(
                @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
            MyProfileResponseDto responseDto = userService.getProfile(userDetails.getUser());
            return new ApiResponse<>(HttpStatus.OK.value(),"내 프로필 조회 성공",responseDto);
        }

        @GetMapping("/profile/{userId}")
        public ApiResponse<UserProfileResponseDto> userProfile(
                @PathVariable Long userId
    ) {
            UserProfileResponseDto responseDto = userService.getUserProfile(userId);
            return new ApiResponse<>(HttpStatus.OK.value(),"유저 프로필 조회 성공",responseDto);
        }

        @PutMapping("/password")
        public ApiResponse<Void> updateIntro(
                @RequestBody PasswordRequestDto passwordRequestDto,
                @AuthenticationPrincipal UserDetailsImpl userDetails) {
                userService.updatePassword(userDetails.getUser(), passwordRequestDto);
                return new ApiResponse<>(HttpStatus.OK.value(),"비밀번호 수정 성공");
            }

            @GetMapping("/logout")
            public ApiResponse<Void> logout(
                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
                userService.logout(userDetails.getUser());
                return new ApiResponse<>(HttpStatus.OK.value(),"로그아웃 되었습니다.");
            }
        }
