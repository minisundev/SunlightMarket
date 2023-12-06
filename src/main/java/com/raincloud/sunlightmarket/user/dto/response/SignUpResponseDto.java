package com.raincloud.sunlightmarket.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpResponseDto {

    private final Long id;
    private final String username;
    private final String email;

    @Builder
    public SignUpResponseDto(final Long id, final String username, final String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
