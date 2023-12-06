package com.raincloud.sunlightmarket.item.dto;

import com.raincloud.sunlightmarket.global.dto.CommonResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class ItemAllResponseDto extends CommonResponseDto {
    List<ItemResponseDto> itemResponseDtos;

    public ItemAllResponseDto(String msg, Integer statuscode){
        super(msg,statuscode);
    }
}
