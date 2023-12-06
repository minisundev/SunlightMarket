package com.raincloud.sunlightmarket.item.controller;

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

    @PostMapping("/post")
    public ResponseEntity<ItemResponseDto> addPost(
        @RequestBody ItemRequestDto requestDto
    ) {
        try {
            ItemResponseDto responseDto = itemService.addItem(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new ItemResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));

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




}
