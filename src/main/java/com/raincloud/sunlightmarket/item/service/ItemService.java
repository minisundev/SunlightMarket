package com.raincloud.sunlightmarket.item.service;

import com.raincloud.sunlightmarket.item.dto.ItemRequestDto;
import com.raincloud.sunlightmarket.item.dto.ItemResponseDto;
import com.raincloud.sunlightmarket.item.dto.ItemUpdateRequest;
import com.raincloud.sunlightmarket.item.entity.Item;
import com.raincloud.sunlightmarket.item.repository.ItemRepository;
import com.raincloud.sunlightmarket.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemResponseDto addItem(ItemRequestDto requestDto, User user) {
        Item item = new Item(requestDto,user);
        itemRepository.save(item);
        return new ItemResponseDto(item);
    }


    @Transactional
    public void updateItem(Long itemId, String title) {
        Item findItem = itemRepository.findById(itemId).orElseThrow(NullPointerException::new);
        findItem.updateTitle(title);
    }

    public void deletePost(Long itemId) {
        Item finditem = itemRepository.findById(itemId).orElseThrow(NullPointerException::new);
        itemRepository.delete(finditem);
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

