package com.raincloud.sunlightmarket.item.entity;

import com.raincloud.sunlightmarket.global.entity.Timestamped;
import com.raincloud.sunlightmarket.item.dto.ItemRequestDto;
import com.raincloud.sunlightmarket.item.dto.ItemUpdateRequest;
import com.raincloud.sunlightmarket.order.entity.Order;
import com.raincloud.sunlightmarket.user.entity.Seller;
import com.raincloud.sunlightmarket.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Getter
@Entity
@Table(name = "items")
@NoArgsConstructor
public class Item extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column
    private String title;

    @Column
    private String image;

    @Column
    private String price;

    @Column
    private String content;

    @Column
    private String address;

    @Column
    private ItemStatus itemstatus;

    @OneToMany(mappedBy = "item",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Order> orders;

    public Item(ItemRequestDto requestDto, Seller seller){
        this.seller = seller;
        title = requestDto.getTitle();
        image = requestDto.getImage();
        price = requestDto.getPrice();
        content = requestDto.getContent();
        address = requestDto.getAddress();
        itemstatus = ItemStatus.ON_SALE;
    }
    public void update(ItemUpdateRequest requestDto) {
        title = requestDto.getTitle();
        image = requestDto.getImage();
        price = requestDto.getPrice();
        content = requestDto.getContent();
        address = requestDto.getAddress();
    }

    public void complete(){
        if(!this.itemstatus.equals(ItemStatus.ON_SALE)){
            throw new RejectedExecutionException("이미 주문이 완료된 아이템입니다");
        }
        this.itemstatus = ItemStatus.SOLD;
    }

    public void confirmDelivery(){
        if(this.itemstatus.equals(ItemStatus.ON_SALE)){
            throw new RejectedExecutionException("주문이 완료되지 않은 아이템입니다");
        }
        if(this.itemstatus.equals(ItemStatus.DELIVERED)){
            throw new RejectedExecutionException("이미 배송이 완료된 아이템입니다");
        }
        this.itemstatus = ItemStatus.DELIVERED;
    }
}
