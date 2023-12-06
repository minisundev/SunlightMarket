package com.raincloud.sunlightmarket.user.service;

import com.raincloud.sunlightmarket.global.entity.UserRoleEnum;
import com.raincloud.sunlightmarket.user.dto.request.ProfileRequestDto;
import com.raincloud.sunlightmarket.user.dto.request.SingUpRequestDto;
import com.raincloud.sunlightmarket.user.dto.response.ProfileResponseDto;
import com.raincloud.sunlightmarket.user.dto.response.SignUpResponseDto;
import com.raincloud.sunlightmarket.user.entity.Buyer;
import com.raincloud.sunlightmarket.user.entity.Seller;
import com.raincloud.sunlightmarket.user.entity.User;
import com.raincloud.sunlightmarket.user.repository.BuyerRepository;
import com.raincloud.sunlightmarket.user.repository.SellerRepository;
import com.raincloud.sunlightmarket.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;

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
        userRepository.save(user);

        Buyer buyer = Buyer.builder()
            .likes(0L)
            .user(user)
            .build();
        buyerRepository.save(buyer);

        Seller seller = Seller.builder()
            .likes(0L)
            .user(user)
            .build();
        sellerRepository.save(seller);

        return SignUpResponseDto.builder()
                .id(user.getId())
                .nickname(nickname)
                .email(email)
                .build();
    }

    @Transactional
    public ProfileResponseDto updateProfile(final User user, final ProfileRequestDto profileRequestDto) {
        Optional<User> findUser = userRepository.findByNickname(profileRequestDto.getNickname());
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
        }

        User loginUser = userRepository.findById(user.getId()).orElseThrow();
        loginUser.updateProfile(profileRequestDto.getNickname(), profileRequestDto.getIntro());

        return ProfileResponseDto.builder()
                .email(loginUser.getEmail())
                .nickname(loginUser.getNickname())
                .intro(loginUser.getIntro())
                .build();
    }
}
