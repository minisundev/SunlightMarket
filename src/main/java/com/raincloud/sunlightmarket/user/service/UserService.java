package com.raincloud.sunlightmarket.user.service;

import com.raincloud.sunlightmarket.global.entity.UserRoleEnum;
import com.raincloud.sunlightmarket.global.jwt.JwtEntity;
import com.raincloud.sunlightmarket.global.jwt.TokenRepository;
import com.raincloud.sunlightmarket.item.dto.ItemResponseDto;
import com.raincloud.sunlightmarket.review.toseller.dto.response.CreateReviewToSellerResponseDto;
import com.raincloud.sunlightmarket.user.dto.request.MyProfileRequestDto;
import com.raincloud.sunlightmarket.user.dto.request.PasswordRequestDto;
import com.raincloud.sunlightmarket.user.dto.request.SingUpRequestDto;
import com.raincloud.sunlightmarket.user.dto.response.MyProfileResponseDto;
import com.raincloud.sunlightmarket.user.dto.response.SignUpResponseDto;
import com.raincloud.sunlightmarket.user.dto.response.UserProfileResponseDto;
import com.raincloud.sunlightmarket.user.entity.Buyer;
import com.raincloud.sunlightmarket.user.entity.Seller;
import com.raincloud.sunlightmarket.user.entity.User;
import com.raincloud.sunlightmarket.user.repository.BuyerRepository;
import com.raincloud.sunlightmarket.user.repository.SellerRepository;
import com.raincloud.sunlightmarket.user.repository.UserRepository;
import java.util.List;
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
    private final TokenRepository tokenRepository;

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
    public MyProfileResponseDto updateProfile(final User user,
        final MyProfileRequestDto myProfileRequestDto) {
        Optional<User> findUser = userRepository.findByNickname(myProfileRequestDto.getNickname());
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 닉네임 입니다.");
        }

        User loginUser = userRepository.findById(user.getId()).orElseThrow(
            () -> new IllegalArgumentException("해당 유자를 찾을 수 없습니다.")
        );
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

    @Transactional
    public UserProfileResponseDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
            () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );

        Seller seller = sellerRepository.findByUserId(userId).orElseThrow(
            () -> new IllegalArgumentException("해당 판매자를 찾을 수 없습니다.")
        );

        List<ItemResponseDto> items = seller.getItems()
            .stream()
            .map(ItemResponseDto::new)
            .toList();

        List<CreateReviewToSellerResponseDto> reviews = seller.getReviews()
            .stream()
            .map(CreateReviewToSellerResponseDto::new)
            .toList();

        return UserProfileResponseDto.builder()
            .id(seller.getId())
            .nickname(seller.getNickname())
            .intro(user.getIntro())
            .likes(seller.getLikes())
            .items(items)
            .reviews(reviews)
            .build();
    }

    @Transactional
    public void updatePassword(final User user, final PasswordRequestDto passwordRequestDto) {
        User loginUser = userRepository.findById(user.getId()).orElseThrow(
            () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );

        if (!passwordEncoder.matches(passwordRequestDto.getCurrentPassword(),
            loginUser.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 일치하지 않아 변경할 수 없습니다.");
        }

        if (!passwordRequestDto.getNewPassword().equals(passwordRequestDto.getCheckNewPassword())) {
            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않아 변경할 수 없습니다.");
        }

        loginUser.updatePassword(passwordEncoder.encode(passwordRequestDto.getNewPassword()));
    }

    @Transactional
    public void logout(User user) {
        JwtEntity jwt = tokenRepository.findByUserId(user.getId());
        tokenRepository.delete(jwt);
    }
}
