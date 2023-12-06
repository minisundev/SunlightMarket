package com.raincloud.sunlightmarket.user.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SingUpRequestDto {

    private final String username;
    private final String password;
    private final String email;
    private final String intro;
    private final boolean admin = false;
    private String adminToken = "";

    @Builder
    public SingUpRequestDto(final String username, final String password, final String email, final String intro) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.intro = intro;
    }
}
