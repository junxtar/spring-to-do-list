package com.sparta.springtodolist.domain.card.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardCompletedResponseDto {

    private Boolean isCompleted;

    @Builder
    private CardCompletedResponseDto(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public static CardCompletedResponseDto of(Boolean isCompleted) {
        return CardCompletedResponseDto.builder()
            .isCompleted(isCompleted)
            .build();
    }

}

