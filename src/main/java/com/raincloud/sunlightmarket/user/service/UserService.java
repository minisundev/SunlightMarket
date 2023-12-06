package com.raincloud.sunlightmarket.user.service;

import com.raincloud.sunlightmarket.global.entity.UserRoleEnum;
import com.raincloud.sunlightmarket.user.dto.request.MyProfileRequestDto;
import com.raincloud.sunlightmarket.user.dto.request.SingUpRequestDto;
import com.raincloud.sunlightmarket.user.dto.response.MyProfileResponseDto;
import com.raincloud.sunlightmarket.user.dto.response.SignUpResponseDto;
import com.raincloud.sunlightmarket.user.entity.User;
import com.raincloud.sunlightmarket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public SignUpResponseDto signUp(SingUpRequestDto singUpRequestDto) {

        String nickname = singUpRequestDto.getNickname();
        String password = passwordEncoder.encode(singUpRequestDto.getPassword());

        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임이 존재합니다.");
        }

        String email = singUpRequestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        UserRoleEnum roleEnum = UserRoleEnum.USER;
        if (singUpRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(singUpRequestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 일치하지 않아 불가능합니다.");
            }
            roleEnum = UserRoleEnum.ADMIN;
        }

        User user = User.builder()
                .nickname(nickname)
                .password(password)
                .email(email)
                .intro(singUpRequestDto.getIntro())
                .role(roleEnum)
                .build();
        user = userRepository.save(user);

        return SignUpResponseDto.builder()
                .id(user.getId())
                .nickname(nickname)
                .email(email)
                .build();
    }

    @Transactional
    public MyProfileResponseDto updateProfile(final User user, final MyProfileRequestDto myProfileRequestDto) {
        Optional<User> findUser = userRepository.findByNickname(myProfileRequestDto.getNickname());
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
        }

        User loginUser = userRepository.findById(user.getId()).orElseThrow();
        loginUser.updateProfile(myProfileRequestDto.getNickname(), myProfileRequestDto.getIntro());

        return MyProfileResponseDto.builder()
                .email(loginUser.getEmail())
                .nickname(loginUser.getNickname())
                .intro(loginUser.getIntro())
                .build();
    }

    public MyProfileResponseDto getProfile(final User user) {
        return MyProfileResponseDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .intro(user.getIntro())
                .build();
    }
}
