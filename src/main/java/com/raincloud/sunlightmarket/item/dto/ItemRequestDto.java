package com.raincloud.sunlightmarket.item.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemRequestDto {
    private String title;
    private String image;
    @Pattern(regexp = "^[0-9]*$", message = "가격은 숫자로만 입력가능합니다.")
    private String price;
    private String address;
    private String content;
}
