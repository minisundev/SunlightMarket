package com.raincloud.sunlightmarket.item.repository;

import com.raincloud.sunlightmarket.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByOrderByCreatedAtDesc();

   Optional<Item> findById(Long itemId);
}
