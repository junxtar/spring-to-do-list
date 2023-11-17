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
    private String username;

    @Builder
    private CardCreateResponseDto(String title, String content, Boolean isCompleted,
        Boolean isPrivated, String username) {
        this.title = title;
        this.content = content;
        this.isCompleted = isCompleted;
        this.isPrivated = isPrivated;
        this.username = username;
    }

    public static CardCreateResponseDto of(Card card, String username) {
        return CardCreateResponseDto.builder()
            .title(card.getTitle())
            .content(card.getContent())
            .isCompleted(card.getIsCompleted())
            .isPrivated(card.getIsPrivated())
            .username(username)
            .build();
    }
}
