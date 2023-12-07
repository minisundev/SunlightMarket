package com.raincloud.sunlightmarket.order.controller;

import com.raincloud.sunlightmarket.global.dto.ApiResponse;
import com.raincloud.sunlightmarket.global.security.UserDetailsImpl;
import com.raincloud.sunlightmarket.order.dto.OrderRequestDto;
import com.raincloud.sunlightmarket.order.dto.OrderResponseDto;
import com.raincloud.sunlightmarket.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/add")
    public ApiResponse<OrderResponseDto> addOrder(
            @RequestBody OrderRequestDto requestDto,
            @RequestParam Long itemId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            OrderResponseDto responseDto = orderService.addOrder(requestDto,itemId, userDetails.getUser());
            return new ApiResponse<OrderResponseDto>(HttpStatus.CREATED.value(),"구매 요청 성공했습니다",responseDto);
        }catch (RejectedExecutionException | IllegalArgumentException ex){
            return new ApiResponse<OrderResponseDto>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        }
    }

}
