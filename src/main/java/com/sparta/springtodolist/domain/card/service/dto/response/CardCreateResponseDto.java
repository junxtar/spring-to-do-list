package com.sparta.springtodolist.domain.card.service.dto.response;

import com.sparta.springtodolist.domain.card.domain.Card;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardCreateResponseDto {

    private String title;
    private String content;
    private Boolean isCompleted;
    private Boolean isPrivated;

    @Builder
    private CardCreateResponseDto(String title, String content, Boolean isCompleted,
        Boolean isPrivated) {
        this.title = title;
        this.content = content;
        this.isCompleted = isCompleted;
        this.isPrivated = isPrivated;
    }

    public static CardCreateResponseDto of(Card card) {
        return CardCreateResponseDto.builder()
            .title(card.getTitle())
            .content(card.getContent())
            .isCompleted(card.getIsCompleted())
            .isPrivated(card.getIsPrivated())
            .build();
    }
}
