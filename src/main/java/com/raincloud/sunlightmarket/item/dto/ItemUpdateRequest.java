package com.raincloud.sunlightmarket.item.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ItemUpdateRequest {
  private String title;
  private String image;
  private String price;
  private String address;
  private String content;
}
