package com.raincloud.sunlightmarket.order.entity;

import com.raincloud.sunlightmarket.global.entity.Timestamped;
import com.raincloud.sunlightmarket.item.entity.Item;
import com.raincloud.sunlightmarket.order.dto.OrderRequestDto;
import com.raincloud.sunlightmarket.user.entity.Buyer;
import com.raincloud.sunlightmarket.user.entity.Seller;
import com.raincloud.sunlightmarket.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.RejectedExecutionException;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    private String address;

    private String orderStatus;

    private String price;

    private Boolean completed;

    public Order(OrderRequestDto requestDto, Item item, Buyer buyer){
        this.item = item;
        this.buyer = buyer;
        this.address = requestDto.getAddress();
        this.orderStatus = "PENDING";
        this.price = requestDto.getPrice();
        this.completed = false;
    }

    public void update(OrderRequestDto requestDto){
        this.address = requestDto.getAddress();
        this.price = requestDto.getPrice();
    }

    public void reject(){
        if(this.completed == true){
            throw new RejectedExecutionException("이미 처리된 요청입니다");
        }
        this.orderStatus = "REJECTED";
        this.completed = true;
    }
}
