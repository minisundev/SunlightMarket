package com.raincloud.sunlightmarket.review.toseller.controller;

import com.raincloud.sunlightmarket.global.dto.ApiResponse;
import com.raincloud.sunlightmarket.global.security.UserDetailsImpl;
import com.raincloud.sunlightmarket.review.toseller.dto.request.ReviewToSellerRequestDto;
import com.raincloud.sunlightmarket.review.toseller.dto.response.CreateReviewToSellerResponseDto;
import com.raincloud.sunlightmarket.review.toseller.dto.response.UpdateReviewToSellerResponseDto;
import com.raincloud.sunlightmarket.review.toseller.service.ReviewToSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{sellerId}/review/to-seller")
public class ReviewToSellerController {

    private final ReviewToSellerService reviewToSellerService;

    @PostMapping
    public ApiResponse<CreateReviewToSellerResponseDto> createReview(
        @PathVariable Long sellerId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody ReviewToSellerRequestDto requestDto) {

        CreateReviewToSellerResponseDto responseDto =
            reviewToSellerService.createReview(sellerId, userDetails.getUser(), requestDto);
        return new ApiResponse<>(HttpStatus.CREATED.value(),"리뷰가 생성되었습니다",responseDto);
    }

    @PutMapping("/{reviewId}")
    public ApiResponse<UpdateReviewToSellerResponseDto> updateReview(
        @PathVariable Long sellerId,
        @PathVariable Long reviewId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody ReviewToSellerRequestDto requestDto) {

        UpdateReviewToSellerResponseDto responseDto =
            reviewToSellerService.updateReview(sellerId, reviewId, userDetails.getUser(),
                requestDto);
        return new ApiResponse<>(HttpStatus.OK.value(),"리뷰가 수정되었습니다",responseDto);
    }

    @PatchMapping("/{reviewId}/like")
    public ApiResponse<Void> clickLike(
        @PathVariable Long sellerId,
        @PathVariable Long reviewId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String message = reviewToSellerService.clickLike(sellerId, reviewId, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(),message);
    }

    @DeleteMapping("/{reviewId}")
    public ApiResponse<Void> deleteReview(
        @PathVariable Long sellerId,
        @PathVariable Long reviewId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        reviewToSellerService.deleteReview(sellerId, reviewId, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(),"해당 리뷰가 삭제 되었습니다.");
    }

}
