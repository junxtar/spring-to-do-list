package com.sparta.springtodolist.domain.card.service.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardCreateServiceRequestDto {

    private String title;
    private String content;
    private Boolean isCompleted;
    private Boolean isPrivated;

    @Builder
    private CardCreateServiceRequestDto(String title, String content, Boolean isCompleted, Boolean isPrivated) {
        this.title = title;
        this.content = content;
        this.isCompleted = isCompleted;
        this.isPrivated = isPrivated;
    }
}
