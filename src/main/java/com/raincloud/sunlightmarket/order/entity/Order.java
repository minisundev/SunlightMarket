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

    public Order(OrderRequestDto requestDto, Item item, Buyer buyer){
        this.item = item;
        this.buyer = buyer;
        this.address = requestDto.getAddress();
        this.orderStatus = "PENDING";
    }
}
