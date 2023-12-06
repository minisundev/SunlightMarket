package com.raincloud.sunlightmarket.item.controller;

import com.raincloud.sunlightmarket.item.dto.ItemAllResponseDto;
import com.raincloud.sunlightmarket.item.dto.ItemRequestDto;
import com.raincloud.sunlightmarket.item.dto.ItemResponseDto;
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
    public ResponseEntity<ItemResponseDto> addItem(
            @RequestBody ItemRequestDto requestDto
    ) {
        try {
            ItemResponseDto responseDto = itemService.addItem(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        }catch (RejectedExecutionException | IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(new ItemResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));

        }
    }

    //선택 상품 조회
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> getItem(
            @PathVariable Long itemId
    ) {
        try {
            ItemResponseDto responseDto = itemService.getItem(itemId);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        }catch (RejectedExecutionException | IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(new ItemResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));

        }
    }


    //전체 상품 조회
    @GetMapping("")
    public ResponseEntity<ItemAllResponseDto> getItems(
            @RequestParam String type
    ) {
        if(type.equals("All")){ return getAllItems();}
        else if(type.equals("Myselect")){return ResponseEntity.badRequest().body(new ItemAllResponseDto("myselect", HttpStatus.BAD_REQUEST.value()));}
        else{return ResponseEntity.badRequest().body(new ItemAllResponseDto("올바르지 않은 요청입니다", HttpStatus.BAD_REQUEST.value()));}
    }

    public ResponseEntity<ItemAllResponseDto> getAllItems()
    {
        try {
            ItemAllResponseDto responseDto = new ItemAllResponseDto();
            responseDto.setItemResponseDtos(itemService.getAllItems());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        }catch (RejectedExecutionException | IllegalArgumentException ex){
            return ResponseEntity.badRequest().body(new ItemAllResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
