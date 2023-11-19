package com.sparta.springtodolist.domain.comment.controller.dto.request;

import com.sparta.springtodolist.domain.comment.service.dto.request.CommentUpdateServiceRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto {

    private String content;

    public CommentUpdateServiceRequestDto toServiceRequest() {
        return CommentUpdateServiceRequestDto.builder()
            .content(content)
            .build();
    }
}
