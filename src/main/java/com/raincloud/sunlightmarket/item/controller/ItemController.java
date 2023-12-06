package com.raincloud.sunlightmarket.item.controller;

import com.raincloud.sunlightmarket.global.dto.ApiResponse;
import com.raincloud.sunlightmarket.item.dto.ItemAllResponseDto;
import com.raincloud.sunlightmarket.item.dto.ItemRequestDto;
import com.raincloud.sunlightmarket.item.dto.ItemResponseDto;
import com.raincloud.sunlightmarket.item.dto.ItemUpdateRequest;
import com.raincloud.sunlightmarket.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    //상품 등록
    @PostMapping("/add")
    public ApiResponse<ItemResponseDto> addItem(
            @RequestBody ItemRequestDto requestDto

    ) {
        try {
            ItemResponseDto responseDto = itemService.addItem(requestDto);
            return new ApiResponse<ItemResponseDto>(HttpStatus.CREATED.value(),"아이템 추가 성공했습니다",responseDto);
        }catch (RejectedExecutionException | IllegalArgumentException ex){
            return new ApiResponse<ItemResponseDto>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        }
    }


    @PutMapping("/items/{itemsId)")
    public ResponseEntity<Void> updatePost(
        @PathVariable Long itemId,
        @RequestBody ItemUpdateRequest request
    ) {
        itemService.updateItem(itemId, request.getTitle());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/items/{itemsId}")
    public ResponseEntity<Void> deletePost(
    @PathVariable Long itemId
    ){
        itemService.deletePost(itemId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //선택 상품 조회
    @GetMapping("/{itemId}")
    public ApiResponse<ItemResponseDto> getItem(
            @PathVariable Long itemId
    ) {
        try {
            ItemResponseDto responseDto = itemService.getItem(itemId);
            return new ApiResponse<ItemResponseDto>(HttpStatus.OK.value(),"아이템 조회에 성공했습니다",responseDto);
        }catch (RejectedExecutionException | IllegalArgumentException ex){
            return new ApiResponse<ItemResponseDto>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        }
    }

    //전체 상품 조회
    @GetMapping("")
    public ApiResponse<ItemAllResponseDto> getItems(
            @RequestParam String type
    ) {
        if(type.equals("All")){ return getAllItems();}
        else if(type.equals("Myselect")){return new ApiResponse<ItemAllResponseDto>(HttpStatus.BAD_REQUEST.value(),"myselect");}
        else{return new ApiResponse<ItemAllResponseDto>(HttpStatus.BAD_REQUEST.value(),"올바르지 않은 요청입니다");}
    }

    public ApiResponse<ItemAllResponseDto> getAllItems()
    {
        try {
            ItemAllResponseDto responseDto = new ItemAllResponseDto();
            responseDto.setItemResponseDtos(itemService.getAllItems());
            return new ApiResponse<ItemAllResponseDto>(HttpStatus.OK.value(),"아이템 조회에 성공했습니다",responseDto);
        }catch (RejectedExecutionException | IllegalArgumentException ex){
            return new ApiResponse<ItemAllResponseDto>(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        }
    }

}
