package com.sparta.springtodolist.domain.card.controller.dto.request;

import com.sparta.springtodolist.domain.card.service.dto.request.CardCreateServiceRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardCreateRequestDto {

    private String title;
    private String content;
    private Boolean isPublic;

    @Builder
    private CardCreateRequestDto(String title, String content, Boolean isPublic) {
        this.title = title;
        this.content = content;
        this.isPublic = isPublic;
    }

    public CardCreateServiceRequestDto toServiceRequest() {
        return CardCreateServiceRequestDto.builder()
            .title(title)
            .content(content)
            .isPublic(isPublic)
            .build();
    }
}
