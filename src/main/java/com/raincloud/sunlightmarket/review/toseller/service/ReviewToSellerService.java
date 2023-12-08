package com.raincloud.sunlightmarket.review.toseller.service;

import com.raincloud.sunlightmarket.review.toseller.dto.request.ReviewToSellerRequestDto;
import com.raincloud.sunlightmarket.review.toseller.dto.response.CreateReviewToSellerResponseDto;
import com.raincloud.sunlightmarket.review.toseller.dto.response.UpdateReviewToSellerResponseDto;
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
@Transactional
public class ReviewToSellerService {

    private final ReviewToSellerRepository reviewToSellerRepository;
    private final SellerRepository sellerRepository;

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

    public UpdateReviewToSellerResponseDto updateReview(
        Long sellerId, Long reviewId, User user, ReviewToSellerRequestDto requestDto) {

        checkedSeller(sellerId);
        ReviewToSeller review = checkedReview(reviewId);
        checkedUser(review, user.getEmail());

        review.update(requestDto);
        return new UpdateReviewToSellerResponseDto(review);
    }

    public String clickLike(Long sellerId, Long reviewId, User user) {
        Seller seller = checkedSeller(sellerId);
        ReviewToSeller review = checkedReview(reviewId);
        checkedUser(review, user.getEmail());

        review.clickLike();
        seller.updateLikeCnt(review.getLiked());
        if (review.getLiked()) {
            return "좋아요가 등록 되었습니다";
        }
        return "좋아요가 취소 되었습니다.";
    }

    public void deleteReview(Long sellerId, Long reviewId, User user) {
        checkedSeller(sellerId);
        ReviewToSeller review = checkedReview(reviewId);
        checkedUser(review, user.getEmail());
        reviewToSellerRepository.delete(review);
    }

    //////////////////////////////////////////////////////////////////////
    private Seller checkedSeller(Long sellerId) {
        return sellerRepository.findById(sellerId).orElseThrow(
            () -> new NullPointerException("판매자 정보가 존재하지 않습니다."));
    }

    private ReviewToSeller checkedReview(Long reivewId) {
        return reviewToSellerRepository.findById(reivewId).orElseThrow(
            () -> new NullPointerException("리뷰 정보가 존재하지 않습니다."));
    }

    private void checkedUser(ReviewToSeller review, String nickname) {
        if (review.getUser().getNickname().equals(nickname)) {
            throw new IllegalArgumentException("회원정보가 일치하지 않습니다.");
        }
    }

    //////////////////////////////////////////////////////////////////////
}
