package com.raincloud.sunlightmarket.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileResponseDto {

    private String email;
    private String nickname;
    private String intro;
}
