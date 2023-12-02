package com.sparta.springtodolist.domain.comment.controller.dto.request;

import com.sparta.springtodolist.domain.comment.service.dto.request.CommentUpdateServiceRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {

    private String content;

    @Builder
    private CommentUpdateRequestDto(String content) {
        this.content = content;
    }

    public CommentUpdateServiceRequestDto toServiceRequest() {
        return CommentUpdateServiceRequestDto.builder()
            .content(content)
            .build();
    }
}
