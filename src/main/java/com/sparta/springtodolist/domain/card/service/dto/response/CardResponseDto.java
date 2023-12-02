package com.sparta.springtodolist.domain.card.service.dto.response;

import com.sparta.springtodolist.domain.card.entity.Card;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardResponseDto {

    private String title;
    private String content;
    private String username;
    private Boolean isCompleted;
    private Boolean isPublic;
    private LocalDateTime createdAt;

    @Builder
    private CardResponseDto(String title, String content, String username, Boolean isCompleted, Boolean isPublic,
        LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.isCompleted = isCompleted;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
    }

    public static CardResponseDto of(Card card) {
        return CardResponseDto.builder()
            .title(card.getTitle())
            .content(card.getContent())
            .username(card.getUser().getUsername())
            .isCompleted(card.getIsCompleted())
            .isPublic(card.getIsPublic())
            .createdAt(card.getCreatedAt())
            .build();
    }
}
