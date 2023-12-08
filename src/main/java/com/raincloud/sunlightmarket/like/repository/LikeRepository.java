package com.raincloud.sunlightmarket.like.repository;

import com.raincloud.sunlightmarket.item.entity.Item;
import com.raincloud.sunlightmarket.like.entity.Like;
import com.raincloud.sunlightmarket.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
  

  void deleteByUserAndItem(User user, Item item);

  boolean existsByUserAndItem(User user, Item item);
}