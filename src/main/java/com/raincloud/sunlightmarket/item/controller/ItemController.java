package com.raincloud.sunlightmarket.item.controller;

import com.raincloud.sunlightmarket.item.dto.ItemAllResponseDto;
import com.raincloud.sunlightmarket.item.dto.ItemRequestDto;
import com.raincloud.sunlightmarket.item.dto.ItemResponseDto;
import com.raincloud.sunlightmarket.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
