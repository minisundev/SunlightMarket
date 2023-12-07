package com.raincloud.sunlightmarket.item.entity;

import com.raincloud.sunlightmarket.global.entity.Timestamped;
import com.raincloud.sunlightmarket.item.dto.ItemRequestDto;
import com.raincloud.sunlightmarket.item.dto.ItemUpdateRequest;
import com.raincloud.sunlightmarket.user.entity.Seller;
import com.raincloud.sunlightmarket.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Table(name = "items")
@NoArgsConstructor
public class Item extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
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
    private Boolean completed;

    @Column
    private Boolean delivered;

    public Item(ItemRequestDto requestDto, Seller seller){
        this.seller = seller;
        title = requestDto.getTitle();
        image = requestDto.getImage();
        price = requestDto.getPrice();
        content = requestDto.getContent();
        address = requestDto.getAddress();
        completed = false;
        delivered = false;
    }
public void update(ItemUpdateRequest requestDto) {
    title = requestDto.getTitle();
    image = requestDto.getImage();
    price = requestDto.getPrice();
    content = requestDto.getContent();
    address = requestDto.getAddress();
    }
}
