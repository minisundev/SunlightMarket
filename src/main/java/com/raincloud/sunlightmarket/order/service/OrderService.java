package com.raincloud.sunlightmarket.order.service;

import com.raincloud.sunlightmarket.global.dto.DoubleResponse;
import com.raincloud.sunlightmarket.item.dto.ItemResponseDto;
import com.raincloud.sunlightmarket.item.entity.Item;
import com.raincloud.sunlightmarket.item.repository.ItemRepository;
import com.raincloud.sunlightmarket.order.dto.OrderRequestDto;
import com.raincloud.sunlightmarket.order.dto.OrderResponseDto;
import com.raincloud.sunlightmarket.order.dto.PublicOrderResponseDto;
import com.raincloud.sunlightmarket.order.entity.Order;
import com.raincloud.sunlightmarket.order.entity.OrderStatus;
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

    public List<PublicOrderResponseDto> getOrders(Long itemId){
        return orderRepository.findAllByItemId(itemId).orElseThrow(()-> new NullPointerException("해당 id로 구매요청을 찾을 수 없습니다."))
                .stream()
                .map(PublicOrderResponseDto::new)
                .collect(Collectors.toList());
    }

    public DoubleResponse<List<OrderResponseDto>,List<PublicOrderResponseDto>> getOrdersForUsers(Long itemId, User user){
        List<Order> orders = orderRepository.findAllByItemId(itemId).orElseThrow(()-> new NullPointerException("해당 id로 구매요청을 찾을 수 없습니다."));
        List<OrderResponseDto> orderDtos = null;
        List<PublicOrderResponseDto> publicOrderDtos = null;
        if(orders.get(0).getItem().getSeller().getId().equals(user.getSeller().getId())){//Item의 작성자가 볼때는 orderId와 address가 포함된 response리턴
            orderDtos = orders.stream()
                    .map(OrderResponseDto::new)
                    .collect(Collectors.toList());
        }else {//Item의 작성자가 아닌 유저가 볼때는 orderId와 address가 제외된 response리턴
            publicOrderDtos = orders.stream()
                    .map(PublicOrderResponseDto::new)
                    .collect(Collectors.toList());
        }
        return new DoubleResponse(orderDtos,publicOrderDtos);
    }

    public List<OrderResponseDto> getAllMyOrders(User user){
        return orderRepository.findAllByBuyerId(user.getBuyer().getId()).orElseThrow(()-> new NullPointerException("주문 요청이 존재하지 않습니다"))
                .stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<OrderResponseDto> getMyConfirmedOrders(User user){
        Long buyerId = user.getBuyer().getId();
        List<Order> orders = orderRepository.findAllByBuyerIdAndOrderStatusEquals(buyerId, OrderStatus.CONFIRMED).orElseThrow(()-> new NullPointerException("주문 요청이 존재하지 않습니다"));
        return orders.stream()
                .map(OrderResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDto updateOrder(OrderRequestDto requestDto, Long orderId, User user){
        Order order = getUserOrderById(orderId,user);
        order.update(requestDto);
        return new OrderResponseDto(order);
    }
    @Transactional
    public OrderResponseDto confirmDelivery(Long orderId,User user){
        Order order = getUserOrderById(orderId,user);
        Item item = order.getItem();
        item.confirmDelivery();
        order.confirmDelivery();
        return new OrderResponseDto(order);
    }

    public OrderResponseDto deleteOrder(Long orderId, User user){
        Order order = getUserOrderById(orderId,user);
        orderRepository.delete(order);
        return new OrderResponseDto(order);
    }

    @Transactional
    public OrderResponseDto rejectOrder(Long orderId, User user){
        Order order = getItemOwnerOrderById(orderId,user);
        order.reject();
        return new OrderResponseDto(order);
    }

    @Transactional
    public OrderResponseDto confirmOrder(Long orderId, User user){
        Order order = getItemOwnerOrderById(orderId,user);
        Item item = order.getItem();
        item.complete();
        order.confirm();
        return new OrderResponseDto(order);
    }

    private Order getUserOrderById(Long orderId,User user){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new NullPointerException("해당 id로 구매요청을 찾을 수 없습니다."));
        if(!order.getBuyer().getUser().getId().equals(user.getId())){
            throw new RejectedExecutionException("작성자만 주문을 수정/삭제할 수 있습니다.");
        }
        return order;
    }

    private Order getItemOwnerOrderById(Long orderId,User user){
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new NullPointerException("해당 id로 구매요청을 찾을 수 없습니다."));
        if(!order.getItem().getSeller().getUser().getId().equals(user.getId())){
            throw new RejectedExecutionException("아이템 작성자만 구매요청을 승인/거절할 수 있습니다.");
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
