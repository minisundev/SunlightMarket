package com.raincloud.sunlightmarket.item.controller;

import com.raincloud.sunlightmarket.global.dto.ApiResponse;
import com.raincloud.sunlightmarket.global.security.UserDetailsImpl;
import com.raincloud.sunlightmarket.item.dto.ItemAllResponseDto;
import com.raincloud.sunlightmarket.item.dto.ItemRequestDto;
import com.raincloud.sunlightmarket.item.dto.ItemResponseDto;
import com.raincloud.sunlightmarket.item.dto.ItemUpdateRequest;
import com.raincloud.sunlightmarket.item.service.ItemService;
import com.raincloud.sunlightmarket.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class  ItemController {

    private final ItemService itemService;

    //상품 등록
    @PostMapping("/add")
    public ApiResponse<ItemResponseDto> addItem(
            @Valid @RequestBody ItemRequestDto requestDto,
            BindingResult bindingResult,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        // Validation 예외 처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            throw new IllegalArgumentException("입력 형식이 정확하지 않습니다");
        }
        ItemResponseDto responseDto = itemService.addItem(requestDto, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.CREATED.value(),"아이템 추가 성공했습니다",responseDto);
    }

    //상품 업데이트
    @PutMapping("")
    public ApiResponse<ItemResponseDto> updatePost(
            @RequestParam Long id,
            @RequestBody ItemUpdateRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        ItemResponseDto responseDto = itemService.updateItem(id, request, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(),"아이템 수정 성공했습니다",responseDto);
    }

    @DeleteMapping("")
    public ApiResponse<Void> deletePost(
            @RequestParam Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        itemService.deletePost(id, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(),"아이템 삭제 성공했습니다");
    }

    //선택 상품 조회
    @GetMapping("/read/{itemId}")
    public ApiResponse<ItemResponseDto> getItem(
            @PathVariable Long itemId
    ) {
        ItemResponseDto responseDto = itemService.getItem(itemId);
        return new ApiResponse<>(HttpStatus.OK.value(),"아이템 조회에 성공했습니다",responseDto);
    }

    //전체 상품 조회
    @GetMapping("")
    public ApiResponse<List<ItemResponseDto>> getItems(
            @RequestParam String type,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        if(type.equals("All")){
            return getAllItems();
        }
        else if(type.equals("Myselect")){
            return getAllLikedItems(userDetails.getUser());
        }
        else{return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(),"올바르지 않은 요청입니다");}
    }

    public ApiResponse<List<ItemResponseDto>> getAllItems()
    {
        List<ItemResponseDto> responseDto = itemService.getAllItems();
        return new ApiResponse<>(HttpStatus.OK.value(),"모든 아이템 조회에 성공했습니다",responseDto);
    }

    public ApiResponse<List<ItemResponseDto>>getAllLikedItems(User user)
    {
        List<ItemResponseDto> responseDto = itemService.getAllLikedItems(user);
        return new ApiResponse<>(HttpStatus.OK.value(),"좋아요한 아이템 조회에 성공했습니다",responseDto);
    }
}
