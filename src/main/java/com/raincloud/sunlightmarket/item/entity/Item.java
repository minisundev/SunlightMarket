package com.raincloud.sunlightmarket.item.entity;

import com.raincloud.sunlightmarket.global.entity.Timestamped;
import com.raincloud.sunlightmarket.item.dto.ItemRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "items")
@NoArgsConstructor
public class Item extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long seller_id;

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

    public Item(ItemRequestDto requestDto){
        this.seller_id = requestDto.getSeller_id();
        this.title = requestDto.getTitle();
        image = requestDto.getImage();
        price = requestDto.getPrice();
        content = requestDto.getContent();
        address = requestDto.getAddress();
        completed = false;
        delivered = false;
    }
public void updateTitle(String title) {
        this.title = title;
}
}
