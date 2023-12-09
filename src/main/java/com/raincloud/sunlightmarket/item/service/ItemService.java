package com.raincloud.sunlightmarket.item.service;

import com.raincloud.sunlightmarket.global.dto.ApiResponse;
import com.raincloud.sunlightmarket.item.dto.ItemRequestDto;
import com.raincloud.sunlightmarket.item.dto.ItemResponseDto;
import com.raincloud.sunlightmarket.item.dto.ItemUpdateRequest;
import com.raincloud.sunlightmarket.item.entity.Item;
import com.raincloud.sunlightmarket.item.repository.ItemRepository;
import com.raincloud.sunlightmarket.like.repository.LikeRepository;
import com.raincloud.sunlightmarket.user.entity.Seller;
import com.raincloud.sunlightmarket.user.entity.User;
import com.raincloud.sunlightmarket.user.repository.SellerRepository;
import com.raincloud.sunlightmarket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final LikeRepository likeRepository;

    public ItemResponseDto addItem(ItemRequestDto requestDto, User user) {
        Seller seller = user.getSeller();
        Item item = new Item(requestDto,seller);
        itemRepository.save(item);
        return new ItemResponseDto(item);
    }

    @Transactional
    public ItemResponseDto updateItem(Long itemId, ItemUpdateRequest request, User user) {
        Item findItem = getUserItem(itemId, user);
        findItem.update(request);
        return new ItemResponseDto(findItem);
    }

    public void deletePost(Long itemId, User user) {
        Item finditem = getUserItem(itemId,user);
        itemRepository.delete(finditem);
    }

    public ItemResponseDto getItem(Long itemId){
        Item item = itemRepository.findById(itemId).orElseThrow(()-> new NullPointerException("해당 id로 아이템을 찾을 수 없습니다."));
        return new ItemResponseDto(item);
    }

    public List<ItemResponseDto> getAllItems() {
        return itemRepository.findAllByOrderByModifiedAtDesc().stream()
                .map(ItemResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ItemResponseDto> getAllLikedItems(User user) {
        List<Item> items =  itemRepository.findAllByOrderByModifiedAtDesc();
        List<ItemResponseDto> responseDtos = new ArrayList<>();
        for(Item item : items){
            if(likeRepository.existsByUserAndItem(user,item)){
                responseDtos.add(new ItemResponseDto(item));
            }
        }
        return  responseDtos;
    }

    private Item getUserItem(Long itemId, User user){
        Item item = itemRepository.findById(itemId).orElseThrow(()-> new NullPointerException("해당 id로 아이템을 찾을 수 없습니다."));
        Seller seller = user.getSeller();
        if(!item.getSeller().getId().equals(seller.getId())){
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return item;
    }
}

