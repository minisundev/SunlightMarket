package com.raincloud.sunlightmarket.review.tobuyer.controller;

import com.raincloud.sunlightmarket.global.security.UserDetailsImpl;
import com.raincloud.sunlightmarket.review.tobuyer.dto.request.ReviewToBuyerRequestDto;
import com.raincloud.sunlightmarket.review.tobuyer.dto.response.CreateReviewToBuyerResponseDto;
import com.raincloud.sunlightmarket.review.tobuyer.dto.response.UpdateReviewToBuyerResponseDto;
import com.raincloud.sunlightmarket.review.tobuyer.service.ReviewToBuyerService;
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
@RequestMapping("/api/{buyerId}/review/to-buyer")
public class ReviewToBuyerController {

    private final ReviewToBuyerService reviewToBuyerService;

    @PostMapping
    public ResponseEntity<CreateReviewToBuyerResponseDto> createReview(
        @PathVariable Long buyerId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody ReviewToBuyerRequestDto requestDto) {

        CreateReviewToBuyerResponseDto responseDto =
            reviewToBuyerService.createReview(buyerId, userDetails.getUser(), requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<UpdateReviewToBuyerResponseDto> updateReview(
        @PathVariable Long buyerId,
        @PathVariable Long reviewId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody ReviewToBuyerRequestDto requestDto) {

        UpdateReviewToBuyerResponseDto responseDto =
            reviewToBuyerService.updateReview(buyerId, reviewId, userDetails.getUser(),
                requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PatchMapping("/{reviewId}/like")
    public ResponseEntity<String> clickLike(
        @PathVariable Long buyerId,
        @PathVariable Long reviewId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String message = reviewToBuyerService.clickLike(buyerId, reviewId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(
        @PathVariable Long buyerId,
        @PathVariable Long reviewId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        reviewToBuyerService.deleteReview(buyerId, reviewId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("해당 리뷰가 삭제 되었습니다.");
    }

}
