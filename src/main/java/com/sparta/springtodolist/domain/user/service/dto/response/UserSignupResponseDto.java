package com.sparta.springtodolist.domain.user.service.dto.response;

import com.sparta.springtodolist.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupResponseDto {

    private String username;
    private String password;

    @Builder
    private UserSignupResponseDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static UserSignupResponseDto of(User user) {
        return UserSignupResponseDto.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .build();
    }
}
