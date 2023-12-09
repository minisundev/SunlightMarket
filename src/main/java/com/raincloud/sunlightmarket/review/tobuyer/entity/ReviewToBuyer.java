package com.raincloud.sunlightmarket.review.tobuyer.entity;

import com.raincloud.sunlightmarket.global.entity.Timestamped;
import com.raincloud.sunlightmarket.review.tobuyer.dto.request.ReviewToBuyerRequestDto;
import com.raincloud.sunlightmarket.user.entity.Buyer;
import com.raincloud.sunlightmarket.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Getter
@RequiredArgsConstructor
@Table(name = "review_to_Buyer")
public class ReviewToBuyer extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    private Boolean liked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    @Builder
    public ReviewToBuyer(String comment, Boolean liked, User user, Buyer buyer) {
        this.comment = comment;
        this.liked = liked;
        this.user = user;
        this.buyer = buyer;
    }

    public void update(ReviewToBuyerRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }

    public Boolean clickLike() {
        this.liked = !liked;
        return this.liked;
    }


}
