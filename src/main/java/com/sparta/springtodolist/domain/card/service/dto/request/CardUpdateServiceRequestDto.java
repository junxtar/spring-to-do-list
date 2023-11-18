package com.sparta.springtodolist.domain.card.service.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardUpdateServiceRequestDto {

    private String title;
    private String content;

    @Builder
    private CardUpdateServiceRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
