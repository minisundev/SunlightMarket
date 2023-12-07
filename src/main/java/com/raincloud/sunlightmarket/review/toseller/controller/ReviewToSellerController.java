package com.raincloud.sunlightmarket.review.toseller.controller;

import com.raincloud.sunlightmarket.global.security.UserDetailsImpl;
import com.raincloud.sunlightmarket.review.toseller.dto.request.ReviewToSellerRequestDto;
import com.raincloud.sunlightmarket.review.toseller.dto.response.CreateReviewToSellerResponseDto;
import com.raincloud.sunlightmarket.review.toseller.service.ReviewToSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{sellerId}/review/to-seller")
public class ReviewToSellerController {

    private final ReviewToSellerService reviewToSellerService;

    @PostMapping
    public ResponseEntity<CreateReviewToSellerResponseDto> createReview(
        @PathVariable Long sellerId,
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody ReviewToSellerRequestDto reqeustDto) {

        CreateReviewToSellerResponseDto responseDto =
            reviewToSellerService.createReview(sellerId, userDetails.getUser(), reqeustDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
