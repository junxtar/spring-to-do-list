package com.sparta.springtodolist.domain.card.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CardTest {

    @DisplayName("카드 수정을 확인한다.")
    @Test
    void cardUpdate() {
        // given
        Card card = Card
            .builder()
            .title("test")
            .content("test")
            .build();
        
        String updateTitle = "updateTitle";
        String updateContent = "updateContent";

        // when
        card.update(updateTitle, updateContent);

        // then
        assertThat(card).extracting("title", "content")
            .contains(updateTitle, updateContent);

    }
    
    @DisplayName("카드의 완료 여부 변경을 확인한다.")
    @Test
    void updateIsCompleted() {
        // given
        Card card = Card
            .builder()
            .isCompleted(false)
            .build();
        
        // when
        card.updateIsCompleted();
        
        // then
        assertThat(card.getIsCompleted()).isTrue();
     }

    @DisplayName("카드의 공개 여부 변경을 확인한다.")
    @Test
    void updateIsPublic() {
        // given
        Card card = Card
            .builder()
            .isPublic(false)
            .build();

        // when
        card.updateIsPublic();

        // then
        assertThat(card.getIsPublic()).isTrue();
    }
}