package com.raincloud.sunlightmarket.order.controller;

import com.raincloud.sunlightmarket.global.dto.ApiResponse;
import com.raincloud.sunlightmarket.global.dto.DoubleResponse;
import com.raincloud.sunlightmarket.global.security.UserDetailsImpl;
import com.raincloud.sunlightmarket.order.dto.OrderRequestDto;
import com.raincloud.sunlightmarket.order.dto.OrderResponseDto;
import com.raincloud.sunlightmarket.order.dto.PublicOrderResponseDto;
import com.raincloud.sunlightmarket.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        }catch (RejectedExecutionException | NullPointerException ex){
            return new ApiResponse<OrderResponseDto>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        }
    }
    @GetMapping("/read")
    public ApiResponse<List<PublicOrderResponseDto>> getOrdersForAll(
            @RequestParam Long itemId
    ) {
        try {
            List<PublicOrderResponseDto> responseDto = orderService.getOrders(itemId);
            return new ApiResponse<List<PublicOrderResponseDto>>(HttpStatus.OK.value(),"구매 요청 조회에 성공했습니다",responseDto);
        }catch (RejectedExecutionException | NullPointerException ex){
            return new ApiResponse<List<PublicOrderResponseDto>>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        }
    }

    @GetMapping("")
    public ApiResponse<DoubleResponse<List<OrderResponseDto>,List<PublicOrderResponseDto>>> getOrdersForUsers(
            @RequestParam Long itemId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            DoubleResponse<List<OrderResponseDto>,List<PublicOrderResponseDto>> responseDto = orderService.getOrdersForUsers(itemId, userDetails.getUser());
            return new ApiResponse<DoubleResponse<List<OrderResponseDto>,List<PublicOrderResponseDto>>>(HttpStatus.OK.value(),"구매 요청 조회에 성공했습니다",responseDto);
        }catch (RejectedExecutionException | NullPointerException ex){
            return new ApiResponse<DoubleResponse<List<OrderResponseDto>,List<PublicOrderResponseDto>>>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        }
    }

    @PutMapping("")
    public ApiResponse<OrderResponseDto> updateOrder(
            @RequestBody OrderRequestDto requestDto,
            @RequestParam Long orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            OrderResponseDto responseDto = orderService.updateOrder(requestDto,orderId,userDetails.getUser());
            return new ApiResponse<OrderResponseDto>(HttpStatus.OK.value(),"구매 요청 업데이트 성공했습니다",responseDto);
        }catch (RejectedExecutionException | NullPointerException ex){
            return new ApiResponse<OrderResponseDto>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        }
    }

    @DeleteMapping("")
    public ApiResponse<OrderResponseDto> deleteOrder(
            @RequestParam Long orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            OrderResponseDto responseDto = orderService.deleteOrder(orderId,userDetails.getUser());
            return new ApiResponse<OrderResponseDto>(HttpStatus.OK.value(),"구매 요청 삭제 성공했습니다",responseDto);
        }catch (RejectedExecutionException | NullPointerException ex){
            return new ApiResponse<OrderResponseDto>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        }
    }

    @PutMapping("/reject")
    public ApiResponse<OrderResponseDto> rejectOrder(
            @RequestParam Long orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            OrderResponseDto responseDto = orderService.rejectOrder(orderId,userDetails.getUser());
            return new ApiResponse<OrderResponseDto>(HttpStatus.OK.value(),"구매 요청 거절 성공했습니다",responseDto);
        }catch (RejectedExecutionException | NullPointerException ex){
            return new ApiResponse<OrderResponseDto>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        }
    }

    @PutMapping("/confirm")
    public ApiResponse<OrderResponseDto> confirmOrder(
            @RequestParam Long orderId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        try {
            OrderResponseDto responseDto = orderService.confirmOrder(orderId,userDetails.getUser());
            return new ApiResponse<OrderResponseDto>(HttpStatus.OK.value(),"구매 요청 승인 성공했습니다",responseDto);
        }catch (RejectedExecutionException | NullPointerException ex){
            return new ApiResponse<OrderResponseDto>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        }
    }
}
