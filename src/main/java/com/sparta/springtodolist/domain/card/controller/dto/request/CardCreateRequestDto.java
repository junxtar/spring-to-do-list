package com.sparta.springtodolist.domain.card.controller.dto.request;

import com.sparta.springtodolist.domain.card.service.dto.request.CardCreateServiceRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardCreateRequestDto {

    private String title;
    private String content;
    private Boolean isPrivated;

    public CardCreateServiceRequestDto toServiceRequest() {
        return CardCreateServiceRequestDto.builder()
            .title(title)
            .content(content)
            .isPrivated(isPrivated)
            .build();
    }
}
