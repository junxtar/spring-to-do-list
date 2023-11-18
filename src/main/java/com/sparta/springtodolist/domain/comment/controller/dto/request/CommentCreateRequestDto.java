package com.sparta.springtodolist.domain.comment.controller.dto.request;

import com.sparta.springtodolist.domain.comment.service.dto.request.CommentCreateServiceRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequestDto {

    private String content;

    public CommentCreateServiceRequestDto toServiceRequest() {
        return CommentCreateServiceRequestDto.builder()
            .content(content)
            .build();
    }
}
