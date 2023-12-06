package com.raincloud.sunlightmarket.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpResponseDto {

    private final Long id;
    private final String nickname;
    private final String email;

    @Builder
    public SignUpResponseDto(final Long id, final String nickname, final String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }
}
