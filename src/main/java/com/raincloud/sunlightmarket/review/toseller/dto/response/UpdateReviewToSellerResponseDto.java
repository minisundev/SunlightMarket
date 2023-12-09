package com.raincloud.sunlightmarket.review.toseller.dto.response;

import com.raincloud.sunlightmarket.review.toseller.entity.ReviewToSeller;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UpdateReviewToSellerResponseDto {

    private Long id;
    private String nickname;
    private String comment;
    private Boolean liked;
    private LocalDateTime date;

    public UpdateReviewToSellerResponseDto(ReviewToSeller review) {
        this.id = review.getId();
        this.nickname = review.getUser().getBuyer().getNickname();
        this.comment = review.getComment();
        this.liked = review.getLiked();
        this.date = review.getModifiedAt();
    }


}
