package com.raincloud.sunlightmarket.order.service;

import com.raincloud.sunlightmarket.item.entity.Item;
import com.raincloud.sunlightmarket.item.repository.ItemRepository;
import com.raincloud.sunlightmarket.order.dto.OrderRequestDto;
import com.raincloud.sunlightmarket.order.dto.OrderResponseDto;
import com.raincloud.sunlightmarket.order.entity.Order;
import com.raincloud.sunlightmarket.order.repository.OrderRepository;
import com.raincloud.sunlightmarket.user.entity.Buyer;
import com.raincloud.sunlightmarket.user.entity.User;
import com.raincloud.sunlightmarket.user.repository.BuyerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final BuyerRepository buyerRepository;

    public OrderResponseDto addOrder(OrderRequestDto requestDto, Long itemId , User user)
    {
        Item item  = getItemById(itemId);
        Buyer buyer = getBuyerByUser(user);
        Order order = new Order(requestDto,item,buyer);
        orderRepository.save(order);
        return new OrderResponseDto(order);
    }

    private Item getItemById(Long itemId){
         Item item = itemRepository.findById(itemId).orElseThrow(NullPointerException::new);
        return item;
    }

    private Buyer getBuyerByUser(User user){
        Buyer buyer = buyerRepository.findByUserId(user.getId()).orElseThrow(NullPointerException::new);
        return buyer;
    }

}
