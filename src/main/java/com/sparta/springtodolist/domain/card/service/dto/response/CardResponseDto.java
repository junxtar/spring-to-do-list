package com.sparta.springtodolist.domain.card.service.dto.response;

import com.sparta.springtodolist.domain.card.entity.Card;
import com.sparta.springtodolist.domain.user.entity.User;
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
    private LocalDateTime createdAt;

    @Builder
    private CardResponseDto(String title, String content, String username, Boolean isCompleted,
        LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.isCompleted = isCompleted;
        this.createdAt = createdAt;
    }

    public static CardResponseDto of(Card card, User user) {
        return CardResponseDto.builder()
            .title(card.getTitle())
            .content(card.getContent())
            .username(user.getUsername())
            .isCompleted(card.getIsCompleted())
            .createdAt(user.getCreatedAt())
            .build();
    }
}
