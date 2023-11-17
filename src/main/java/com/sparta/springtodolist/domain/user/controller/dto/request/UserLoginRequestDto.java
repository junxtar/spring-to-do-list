package com.sparta.springtodolist.domain.user.controller.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginRequestDto {

    @NotBlank(message = "유저이름은 필수 값 입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 값 입니다.")
    private String password;

}
