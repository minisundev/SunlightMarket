package com.raincloud.sunlightmarket.order.repository;

import com.raincloud.sunlightmarket.order.entity.Order;
import com.raincloud.sunlightmarket.order.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<List<Order>> findAllByItemId(Long itemId);
    Optional<Order> findById(Long orderId);
    Optional<List<Order>> findAllByBuyerId(Long buyerId);

    Optional<List<Order>> findAllByBuyerIdAndOrderStatusEquals(Long buyerId, OrderStatus orderStatus);
}
