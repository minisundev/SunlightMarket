package com.raincloud.sunlightmarket.like.service;

import com.raincloud.sunlightmarket.item.entity.Item;
import com.raincloud.sunlightmarket.item.repository.ItemRepository;
import com.raincloud.sunlightmarket.like.entity.Like;
import com.raincloud.sunlightmarket.like.repository.LikeRepository;
import com.raincloud.sunlightmarket.user.entity.User;
import com.raincloud.sunlightmarket.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
  private final LikeRepository likeRepository;
  private final ItemRepository itemRepository;
  private final UserRepository userRepository;


  @Transactional
  public void createLikeForItem(Long userId , Long itemId) {
    Item item = itemRepository.findById(itemId)
        .orElseThrow(() -> new EntityNotFoundException("TIL을 찾을 수 없습니다."));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

    if (!likeRepository.existsByUserAndItem(user, item)) {
      likeRepository.save(Like.builder().user(user).item(item).build());
    }
  }

  @Transactional
  public void deleteLikeForItem(Long userId, Long itemId) {
    Item item = itemRepository.findById(itemId)
        .orElseThrow(() -> new EntityNotFoundException("ITEM 을 찾을 수 없습니다."));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("ITEM 을 찾을 수 없습니다."));

            if(likeRepository.existsByUserAndItem(user,item)) {
                likeRepository.deleteByUserAndItem(user,item);
            }

  }


















}