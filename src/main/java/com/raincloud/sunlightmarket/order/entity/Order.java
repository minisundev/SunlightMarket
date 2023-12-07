package com.raincloud.sunlightmarket.order.entity;

import com.raincloud.sunlightmarket.global.entity.Timestamped;
import com.raincloud.sunlightmarket.item.entity.Item;
import com.raincloud.sunlightmarket.user.entity.Buyer;
import com.raincloud.sunlightmarket.user.entity.Seller;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@RequiredArgsConstructor
public class Order extends Timestamped {
    private final int ORDER_PENDING = 1;
    private final int ORDER_ACCEPTED = 2;
    private final int ORDER_REJECTED = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Buyer buyer;

    private int orderStatus;

}
