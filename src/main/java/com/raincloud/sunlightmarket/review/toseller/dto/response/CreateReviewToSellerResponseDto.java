package com.raincloud.sunlightmarket.review.toseller.dto.response;

import com.raincloud.sunlightmarket.review.toseller.entity.ReviewToSeller;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CreateReviewToSellerResponseDto {

    private Long id;
    private String nickname;
    private String comment;
    private Boolean liked;
    private LocalDateTime date;

    public CreateReviewToSellerResponseDto(ReviewToSeller review) {
        this.id = review.getId();
        this.nickname = review.getUser().getBuyer().getNickname();
        this.comment = review.getComment();
        this.liked = review.getLiked();
        this.date = review.getCreatedAt();
    }
}
