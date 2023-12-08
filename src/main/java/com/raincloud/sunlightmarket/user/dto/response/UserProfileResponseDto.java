package com.raincloud.sunlightmarket.user.dto.response;

import com.raincloud.sunlightmarket.item.dto.ItemResponseDto;
import com.raincloud.sunlightmarket.review.toseller.dto.response.CreateReviewToSellerResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserProfileResponseDto {

    private Long id;
    private String nickname;
    private String intro;
    private Long likes;
    private List<ItemResponseDto> items;
    private List<CreateReviewToSellerResponseDto> reviews;
}
