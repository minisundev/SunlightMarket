package com.raincloud.sunlightmarket.global.dto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DoubleResponse<T,U> {

    private T data1;
    private U data2;

    public DoubleResponse(T data1, U data2){
        this.data1 = data1;
        this.data2 = data2;
    }
}
