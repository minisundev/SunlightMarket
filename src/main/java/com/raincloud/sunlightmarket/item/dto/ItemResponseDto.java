package com.raincloud.sunlightmarket.item.dto;

import com.raincloud.sunlightmarket.global.dto.CommonResponseDto;
import com.raincloud.sunlightmarket.item.entity.Item;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ItemResponseDto extends CommonResponseDto {
    private Long seller_id;
    private String title;
    private String image;
    private String price;
    private String address;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    public ItemResponseDto(Item item) {
        this.title = item.getTitle();
        this.seller_id = item.getSeller_id();
        this.content = item.getContent();
        this.image = item.getImage();
        this.price = item.getPrice();
        this.address = item.getAddress();
        this.created_at = item.getCreatedAt();
        this.modified_at = item.getModifiedAt();
    }

    public ItemResponseDto(String msg, Integer statuscode){
        super(msg,statuscode);

    }
}
