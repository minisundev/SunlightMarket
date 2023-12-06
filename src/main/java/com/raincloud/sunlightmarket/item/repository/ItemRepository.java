package com.raincloud.sunlightmarket.item.repository;

import com.raincloud.sunlightmarket.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
