package com.sparta.springtodolist.domain.user.controller.dto.request;

import com.sparta.springtodolist.domain.user.service.dto.request.UserSignupServiceRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {

    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    @NotBlank
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    @NotBlank
    private String password;

    public UserSignupServiceRequestDto toServiceRequest() {
        return UserSignupServiceRequestDto.builder()
            .username(username)
            .password(password)
            .build();
    }
}
