package com.sparta.springtodolist.domain.comment.service.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateServiceRequestDto {

    private String content;

    @Builder
    private CommentUpdateServiceRequestDto(String content) {
        this.content = content;
    }
}
