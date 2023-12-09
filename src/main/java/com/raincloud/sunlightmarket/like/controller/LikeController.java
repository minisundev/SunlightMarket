package com.raincloud.sunlightmarket.like.controller;

import com.raincloud.sunlightmarket.global.dto.ApiResponse;
import com.raincloud.sunlightmarket.global.security.UserDetailsImpl;
import com.raincloud.sunlightmarket.like.dto.LikeDTO;
import com.raincloud.sunlightmarket.like.entity.Like;
import com.raincloud.sunlightmarket.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/items")

public class LikeController {
  private final LikeService likeService;

  @PostMapping("/{itemId}")
  public ApiResponse<Void> createLikeForItem(
      @PathVariable Long itemId,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    likeService.createLikeForItem(userDetails.getUser().getId(),itemId);
    return new ApiResponse<>(HttpStatus.OK.value(),"좋아요 성공");
  }

  @DeleteMapping("/{itemId}")
  public ApiResponse<Void> deleteLikeForPost(
      @PathVariable Long itemId,
      @AuthenticationPrincipal UserDetailsImpl userDetails)  {
    likeService.deleteLikeForItem(userDetails.getUser().getId(),itemId);
    return new ApiResponse<>(HttpStatus.OK.value(),"좋아요 취소");
  }
}

