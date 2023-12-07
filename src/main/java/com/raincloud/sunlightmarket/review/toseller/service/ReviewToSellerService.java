package com.raincloud.sunlightmarket.review.toseller.service;

import com.raincloud.sunlightmarket.review.toseller.dto.request.ReviewToSellerRequestDto;
import com.raincloud.sunlightmarket.review.toseller.dto.response.CreateReviewToSellerResponseDto;
import com.raincloud.sunlightmarket.review.toseller.entity.ReviewToSeller;
import com.raincloud.sunlightmarket.review.toseller.repository.ReviewToSellerRepository;
import com.raincloud.sunlightmarket.user.entity.Seller;
import com.raincloud.sunlightmarket.user.entity.User;
import com.raincloud.sunlightmarket.user.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewToSellerService {

    private final ReviewToSellerRepository reviewToSellerRepository;
    private final SellerRepository sellerRepository;

    @Transactional
    public CreateReviewToSellerResponseDto createReview(
        Long sellerId, User user, ReviewToSellerRequestDto requestDto) {

        Seller seller = checkedSeller(sellerId);
        ReviewToSeller review = ReviewToSeller.builder()
            .comment(requestDto.getComment())
            .liked(false)
            .user(user)
            .seller(seller)
            .build();
        reviewToSellerRepository.save(review);
        return new CreateReviewToSellerResponseDto(review);
    }

    //////////////////////////////////////////////////////////////////////
    private Seller checkedSeller(Long sellerId) {
        return sellerRepository.findById(sellerId).orElseThrow(
            () -> new NullPointerException("판매자 정보가 존재하지 않습니다."));
    }
    //////////////////////////////////////////////////////////////////////
}
