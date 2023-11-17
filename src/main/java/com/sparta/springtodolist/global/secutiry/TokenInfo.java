package com.sparta.springtodolist.global.secutiry;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenInfo {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}
