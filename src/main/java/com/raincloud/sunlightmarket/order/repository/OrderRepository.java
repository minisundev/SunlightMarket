package com.raincloud.sunlightmarket.order.repository;

import com.raincloud.sunlightmarket.item.entity.Item;
import com.raincloud.sunlightmarket.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
