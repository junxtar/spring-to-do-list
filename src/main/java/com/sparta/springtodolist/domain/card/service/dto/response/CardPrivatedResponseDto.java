package com.sparta.springtodolist.domain.card.service.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardPrivatedResponseDto {

    private Boolean isPublic;

    @Builder
    private CardPrivatedResponseDto(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public static CardPrivatedResponseDto of(Boolean isPublic) {
        return CardPrivatedResponseDto.builder()
            .isPublic(isPublic)
            .build();
    }
}
