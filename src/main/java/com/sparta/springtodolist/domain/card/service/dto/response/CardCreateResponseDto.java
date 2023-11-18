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
public class CardCreateResponseDto {

    private String title;
    private String content;
    private String username;
    private LocalDateTime createdAt;

    @Builder
    private CardCreateResponseDto(String title, String content, String username, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }

    public static CardCreateResponseDto of(Card card, User user) {
        return CardCreateResponseDto.builder()
            .title(card.getTitle())
            .content(card.getContent())
            .username(user.getUsername())
            .createdAt(user.getCreatedAt())
            .build();
    }
}
