package com.sparta.springtodolist.domain.user.controller.dto.request;

import com.sparta.springtodolist.domain.user.service.dto.request.UserSignupServiceRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {

    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "유저 이름은 영 소문자, 숫자를 포함한 4글자 이상 10글자 이하입니다.")
    @NotBlank(message = "유저이름은 필수 값 입니다.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 영문자, 숫자를 포함한 8글자 이상 15글자 이하입니다.")
    @NotBlank(message = "비밀번호는 필수 값 입니다.")
    private String password;

    public UserSignupServiceRequestDto toServiceRequest() {
        return UserSignupServiceRequestDto.builder()
            .username(username)
            .password(password)
            .build();
    }
}
