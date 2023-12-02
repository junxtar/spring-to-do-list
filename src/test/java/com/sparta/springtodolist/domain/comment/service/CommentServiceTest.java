package com.sparta.springtodolist.domain.comment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sparta.springtodolist.domain.card.entity.Card;
import com.sparta.springtodolist.domain.card.service.CardService;
import com.sparta.springtodolist.domain.comment.controller.dto.request.CommentCreateRequestDto;
import com.sparta.springtodolist.domain.comment.controller.dto.request.CommentUpdateRequestDto;
import com.sparta.springtodolist.domain.comment.entity.Comment;
import com.sparta.springtodolist.domain.comment.exception.CommentNotAccessException;
import com.sparta.springtodolist.domain.comment.exception.CommentNotFoundException;
import com.sparta.springtodolist.domain.comment.repository.CommentRepository;
import com.sparta.springtodolist.domain.comment.service.dto.resopnse.CommentResponseDto;
import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.global.exception.ErrorCode;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;

    @Mock
    CardService cardService;

    @Mock
    CommentRepository commentRepository;

    private User user, user2;
    private Card card;
    private Comment comment1, comment2;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .username("test1")
            .password("test1234")
            .build();

        user2 = User.builder()
            .id(2L)
            .username("test1")
            .password("test1234")
            .build();

        card = Card.builder()
            .id(1L)
            .title("test1")
            .content("testContent1")
            .user(user)
            .isCompleted(true)
            .isPublic(true)
            .build();

        comment1 = Comment.builder()
            .id(1L)
            .content("test1")
            .card(card)
            .user(user)
            .build();

        comment2 = Comment.builder()
            .id(2L)
            .content("test2")
            .build();
    }

    @DisplayName("댓글을 생성한다.")
    @Test
    void createComment() {
        // given
        CommentCreateRequestDto request = CommentCreateRequestDto.builder()
            .content("test1")
            .build();

        given(commentRepository.save(any(Comment.class))).willReturn(comment1);

        // when
        CommentResponseDto actual = commentService.createComment(1L, request.toServiceRequest(),
            user);

        // then
        assertThat(actual).extracting("content").isEqualTo("test1");
    }

    @DisplayName("존재하지 않는 댓글을 수정한다.")
    @Test
    void updateNotExistsComment() {
        // given
        CommentUpdateRequestDto request = CommentUpdateRequestDto.builder()
            .content("update")
            .build();

        given(commentRepository.findById(anyLong())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> commentService.updateComment(1L, request.toServiceRequest(), user))
            .isInstanceOf(CommentNotFoundException.class)
            .hasMessage(ErrorCode.COMMENT_NOT_FOUND.getMessage());
    }

    @DisplayName("다른 사용자의 댓글을 수정한다.")
    @Test
    void updateNotAccessComment() {
        // given
        CommentUpdateRequestDto request = CommentUpdateRequestDto.builder()
            .content("update")
            .build();

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment1));

        // when, then
        assertThatThrownBy(
            () -> commentService.updateComment(1L, request.toServiceRequest(), user2))
            .isInstanceOf(CommentNotAccessException.class)
            .hasMessage(ErrorCode.COMMENT_NOT_ACCESS.getMessage());
    }

    @DisplayName("댓글을 수정한다.")
    @Test
    void updateComment() {
        // given
        CommentUpdateRequestDto request = CommentUpdateRequestDto.builder()
            .content("update")
            .build();

        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment1));

        // when
        CommentResponseDto actual = commentService.updateComment(1L,
            request.toServiceRequest(), user);
        // then
        assertThat(actual).extracting("content").isEqualTo("update");
    }

    @DisplayName("댓글을 삭제한다.")
    @Test
    void deleteComment() {
        // given
        given(commentRepository.findById(anyLong())).willReturn(Optional.of(comment1));

        // when
        commentService.deleteComment(1L, user);

        // then
        verify(commentRepository, times(1)).delete(any(Comment.class));
     }
}