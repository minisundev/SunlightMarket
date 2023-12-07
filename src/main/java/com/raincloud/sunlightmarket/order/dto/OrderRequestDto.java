package com.raincloud.sunlightmarket.order.dto;

import com.raincloud.sunlightmarket.item.entity.Item;
import com.raincloud.sunlightmarket.user.entity.Buyer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderRequestDto {

    private String address;
    private String price;

}
