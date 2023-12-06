package com.raincloud.sunlightmarket.item.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemRequestDto {
    private String title;
    private String image;
    private String price;
    private String address;
    private String content;
}
