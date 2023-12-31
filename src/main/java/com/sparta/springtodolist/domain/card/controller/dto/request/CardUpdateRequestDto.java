package com.sparta.springtodolist.domain.card.controller.dto.request;

import com.sparta.springtodolist.domain.card.service.dto.request.CardUpdateServiceRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardUpdateRequestDto {

    private String title;
    private String content;

    @Builder
    private CardUpdateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public CardUpdateServiceRequestDto toServiceRequest() {
        return CardUpdateServiceRequestDto.builder()
            .title(title)
            .content(content)
            .build();
    }
}
