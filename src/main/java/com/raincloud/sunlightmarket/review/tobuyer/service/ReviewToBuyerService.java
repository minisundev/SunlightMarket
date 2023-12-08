package com.raincloud.sunlightmarket.review.tobuyer.service;

import com.raincloud.sunlightmarket.review.tobuyer.dto.request.ReviewToBuyerRequestDto;
import com.raincloud.sunlightmarket.review.tobuyer.dto.response.CreateReviewToBuyerResponseDto;
import com.raincloud.sunlightmarket.review.tobuyer.dto.response.UpdateReviewToBuyerResponseDto;
import com.raincloud.sunlightmarket.review.tobuyer.entity.ReviewToBuyer;
import com.raincloud.sunlightmarket.review.tobuyer.repository.ReviewToBuyerRepository;
import com.raincloud.sunlightmarket.user.entity.Buyer;
import com.raincloud.sunlightmarket.user.entity.User;
import com.raincloud.sunlightmarket.user.repository.BuyerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewToBuyerService {

    private final ReviewToBuyerRepository reviewToBuyerRepository;
    private final BuyerRepository buyerRepository;

    public CreateReviewToBuyerResponseDto createReview(
        Long buyerId, User user, ReviewToBuyerRequestDto requestDto) {

        Buyer buyer = checkedBuyer(buyerId);
        ReviewToBuyer review = ReviewToBuyer.builder()
            .comment(requestDto.getComment())
            .liked(false)
            .user(user)
            .buyer(buyer)
            .build();
        reviewToBuyerRepository.save(review);
        return new CreateReviewToBuyerResponseDto(review);
    }

    public UpdateReviewToBuyerResponseDto updateReview(
        Long buyerId, Long reviewId, User user, ReviewToBuyerRequestDto requestDto) {

        checkedBuyer(buyerId);
        ReviewToBuyer review = checkedReview(reviewId);
        checkedUser(review, user.getEmail());

        review.update(requestDto);
        return new UpdateReviewToBuyerResponseDto(review);
    }

    public String clickLike(Long buyerId, Long reviewId, User user) {
        Buyer buyer = checkedBuyer(buyerId);
        ReviewToBuyer review = checkedReview(reviewId);
        checkedUser(review, user.getEmail());

        review.clickLike();
        buyer.updateLikeCnt(review.getLiked());
        if (review.getLiked()) {
            return "좋아요가 등록 되었습니다";
        }
        return "좋아요가 취소 되었습니다.";
    }

    public void deleteReview(Long buyerId, Long reviewId, User user) {
        checkedBuyer(buyerId);
        ReviewToBuyer review = checkedReview(reviewId);
        checkedUser(review, user.getEmail());
        reviewToBuyerRepository.delete(review);
    }

    //////////////////////////////////////////////////////////////////////
    private Buyer checkedBuyer(Long buyerId) {
        return buyerRepository.findById(buyerId).orElseThrow(
            () -> new NullPointerException("구매자 정보가 존재하지 않습니다."));
    }

    private ReviewToBuyer checkedReview(Long reviewId) {
        return reviewToBuyerRepository.findById(reviewId).orElseThrow(
            () -> new NullPointerException("리뷰 정보가 존재하지 않습니다."));
    }

    private void checkedUser(ReviewToBuyer review, String nickname) {
        if (review.getUser().getNickname().equals(nickname)) {
            throw new IllegalArgumentException("회원정보가 일치하지 않습니다.");
        }
    }

    //////////////////////////////////////////////////////////////////////
}
