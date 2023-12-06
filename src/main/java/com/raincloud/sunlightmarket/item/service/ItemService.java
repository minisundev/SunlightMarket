package com.raincloud.sunlightmarket.item.service;

import com.raincloud.sunlightmarket.item.dto.ItemRequestDto;
import com.raincloud.sunlightmarket.item.dto.ItemResponseDto;
import com.raincloud.sunlightmarket.item.dto.ItemUpdateRequest;
import com.raincloud.sunlightmarket.item.entity.Item;
import com.raincloud.sunlightmarket.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemResponseDto addItem(ItemRequestDto requestDto) {
        // Dto -> Entity
        Item item = new Item(requestDto);
        Item saveItem = itemRepository.save(item);
        return new ItemResponseDto(item);
    }


    @Transactional
    public void updateItem(Long itemId, String title) {
        Item findItem = itemRepository.findById(itemId).orElseThrow(NullPointerException::new);
        findItem.updateTitle(title);
    }
}


