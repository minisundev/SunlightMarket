package com.raincloud.sunlightmarket.item.service;

import com.raincloud.sunlightmarket.item.dto.ItemRequestDto;
import com.raincloud.sunlightmarket.item.dto.ItemResponseDto;
import com.raincloud.sunlightmarket.item.entity.Item;
import com.raincloud.sunlightmarket.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public ItemResponseDto getItem(Long itemId){
        Item item = itemRepository.findById(itemId).orElse(null);
        return new ItemResponseDto(item);
    }

    public List<ItemResponseDto> getAllItems() {
        return itemRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(ItemResponseDto::new)
                .collect(Collectors.toList());
    }
}
