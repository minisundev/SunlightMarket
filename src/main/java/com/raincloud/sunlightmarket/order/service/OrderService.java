package com.raincloud.sunlightmarket.order.service;

import com.raincloud.sunlightmarket.item.entity.Item;
import com.raincloud.sunlightmarket.item.repository.ItemRepository;
import com.raincloud.sunlightmarket.order.dto.OrderRequestDto;
import com.raincloud.sunlightmarket.order.dto.OrderResponseDto;
import com.raincloud.sunlightmarket.order.entity.Order;
import com.raincloud.sunlightmarket.order.repository.OrderRepository;
import com.raincloud.sunlightmarket.user.entity.Buyer;
import com.raincloud.sunlightmarket.user.entity.Seller;
import com.raincloud.sunlightmarket.user.entity.User;
import com.raincloud.sunlightmarket.user.repository.BuyerRepository;
import com.raincloud.sunlightmarket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final BuyerRepository buyerRepository;
    private final UserRepository userRepository;

    public OrderResponseDto addOrder(OrderRequestDto requestDto, Long itemId , User user)
    {
        Item item  = getNotUserItemById(itemId,user);
        Buyer buyer = getBuyerByUser(user);
        Order order = new Order(requestDto,item,buyer);
        orderRepository.save(order);
        return new OrderResponseDto(order);
    }

    private Item getItemById(Long itemId){
         Item item = itemRepository.findById(itemId).orElseThrow(NullPointerException::new);
        return item;
    }

    private Item getNotUserItemById(Long itemId, User user){
        Item item = itemRepository.findById(itemId).orElseThrow(NullPointerException::new);
        User userFound = item.getSeller().getUser();
        if(userFound.getId().equals(user.getId())){
            throw new RejectedExecutionException("작성자는 구매 요청을 할 수 없습니다.");
        }
        return item;
    }

    private Buyer getBuyerByUser(User user){
        Buyer buyer = buyerRepository.findByUserId(user.getId()).orElseThrow(NullPointerException::new);
        return buyer;
    }
}
