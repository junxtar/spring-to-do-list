package com.sparta.springtodolist.domain.card.service.dto.response;

import com.sparta.springtodolist.domain.card.entity.Card;
import com.sparta.springtodolist.domain.comment.service.dto.resopnse.CommentResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SingleCardResponseDto {

    private String title;
    private String content;
    private String username;
    private Boolean isCompleted;
    private Boolean isPublic;
    private List<CommentResponseDto> commentList;
    private LocalDateTime createdAt;

    @Builder
    private SingleCardResponseDto(String title, String content, String username,
        Boolean isCompleted,
        Boolean isPublic, List<CommentResponseDto> commentList, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.isCompleted = isCompleted;
        this.isPublic = isPublic;
        this.commentList = commentList;
        this.createdAt = createdAt;
    }

    public static SingleCardResponseDto of(Card card, List<CommentResponseDto> commentList) {
        return SingleCardResponseDto.builder()
            .title(card.getTitle())
            .content(card.getContent())
            .username(card.getUser().getUsername())
            .isCompleted(card.getIsCompleted())
            .isPublic(card.getIsPublic())
            .commentList(commentList)
            .createdAt(card.getCreatedAt())
            .build();
    }
}
