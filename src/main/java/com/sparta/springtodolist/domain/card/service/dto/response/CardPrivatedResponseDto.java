package com.sparta.springtodolist.domain.card.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardPrivatedResponseDto {

    private Boolean isPrivated;

    @Builder
    private CardPrivatedResponseDto(Boolean isPrivated) {
        this.isPrivated = isPrivated;
    }

    public static CardPrivatedResponseDto of(Boolean isPrivated) {
        return CardPrivatedResponseDto.builder()
            .isPrivated(isPrivated)
            .build();
    }
}
