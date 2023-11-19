package com.sparta.springtodolist.domain.user.service.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSignupServiceRequestDto {

    private String username;
    private String password;

    @Builder
    private UserSignupServiceRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
