package com.sparta.springtodolist.domain.comment.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {

    @DisplayName("댓글의 내용이 수정이 되었는지 확인한다.")
    @Test
    void updateComment() {
        // given
        Comment comment = Comment.builder()
            .content("test")
            .build();
        String updateComment = "update";

        // when
        comment.update(updateComment);

        // then
        assertThat(comment.getContent()).isEqualTo(updateComment);
     }

}