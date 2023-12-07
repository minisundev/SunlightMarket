package com.raincloud.sunlightmarket.order.service;

import com.raincloud.sunlightmarket.item.dto.ItemResponseDto;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

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
        Buyer buyer = user.getBuyer();
        Order order = new Order(requestDto,item,buyer);
        orderRepository.save(order);
        return new OrderResponseDto(order);
    }

    public List<OrderResponseDto> getOrders(Long itemId){
        return orderRepository.findAllByItemId(itemId).orElseThrow(()-> new NullPointerException("해당 id로 구매요청을 찾을 수 없습니다."))
                .stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDto updateOrder(OrderRequestDto requestDto, Long orderId, User user){
        Order order = getUserOrderById(orderId,user);
        order.update(requestDto);
        return new OrderResponseDto(order);
    }

    private Order getUserOrderById(Long orderId,User user){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new NullPointerException("해당 id로 구매요청을 찾을 수 없습니다."));
        if(!order.getBuyer().getUser().getId().equals(user.getId())){
            throw new RejectedExecutionException("작성자만 구매요청을 수정할 수 있습니다.");
        }
        return order;
    }

    private Item getNotUserItemById(Long itemId, User user){
        Item item = itemRepository.findById(itemId).orElseThrow(()-> new NullPointerException("해당 id로 아이템을 찾을 수 없습니다."));
        User userFound = item.getSeller().getUser();
        if(userFound.getId().equals(user.getId())){
            throw new RejectedExecutionException("작성자는 구매요청을 할 수 없습니다.");
        }
        return item;
    }

}
