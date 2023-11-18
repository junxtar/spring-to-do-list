package com.sparta.springtodolist.domain.comment.service.dto.resopnse;

import com.sparta.springtodolist.domain.comment.entity.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDto {

    private String content;

    @Builder
    private CommentResponseDto(String content) {
        this.content = content;
    }

    public static CommentResponseDto of(Comment comment) {
        return CommentResponseDto.builder()
            .content(comment.getContent())
            .build();
    }
}
