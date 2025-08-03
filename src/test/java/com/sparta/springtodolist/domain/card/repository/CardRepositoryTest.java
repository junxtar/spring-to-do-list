package com.sparta.springtodolist.domain.card.repository;

import com.sparta.springtodolist.RepositoryTestSupport;
import com.sparta.springtodolist.domain.card.entity.Card;
import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.domain.user.repository.UserRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CardRepositoryTest extends RepositoryTestSupport {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("생성 일자 기준으로 내림차순으로 카드 리스트를 조회한다.")
    @Test
    void findAllByOrderByCreatedAtDesc() {
        // given
        Card card1 = Card.builder()
            .title("test1")
            .build();

        Card card2 = Card.builder()
            .title("test2")
            .build();

        cardRepository.saveAll(List.of(card1, card2));

        // when
        List<Card> actual = cardRepository.findAllByOrderByCreatedAtDesc();

        // then
        Assertions.assertThat(actual).hasSize(2);
        Assertions.assertThat(actual.get(0).getTitle()).isEqualTo("test2");
     }

    @DisplayName("생성 일자 기준으로 내림차순으로 빈 카드 리스트를 조회한다.")
    @Test
    void findAllByOrderByCreatedAtDescOfZero() {
        // when
        List<Card> actual = cardRepository.findAllByOrderByCreatedAtDesc();

        // then
        Assertions.assertThat(actual).hasSize(0);

    }

    @DisplayName("카드 제목을 키원드로 검색해 카드 리스트를 조회한다.")
    @Test
    void findCardsByTitleContaining() {
        // given
        Card card1 = Card.builder()
            .title("test1")
            .build();

        Card card2 = Card.builder()
            .title("test2")
            .build();

        cardRepository.saveAll(List.of(card1, card2));

        // when
        List<Card> actual = cardRepository.findCardsByTitleContaining("test");

        // then
        Assertions.assertThat(actual).hasSize(2);
    }

    @DisplayName("카드 제목을 존재하지 않는 키워드로 찾아서 빈카드 리스트를 조회한다.")
    @Test
    void findCardsByTitleNotContaining() {
        // given
        Card card1 = Card.builder()
            .title("test1")
            .build();

        Card card2 = Card.builder()
            .title("test2")
            .build();

        cardRepository.saveAll(List.of(card1, card2));

        // when
        List<Card> actual = cardRepository.findCardsByTitleContaining("TEST");

        // then
        Assertions.assertThat(actual).hasSize(0);
    }

    @DisplayName("유저의 아이디로 카드를 삭제한다.")
    @Test
    void deleteAllByUser_Id() {
        // given
        User user = User.builder()
            .id(1L)
            .username("test")
            .build();

        Card card1 = Card.builder()
            .title("test1")
            .user(user)
            .build();

        Card card2 = Card.builder()
            .title("test2")
            .user(user)
            .build();

        Card card3 = Card.builder()
            .title("test3")
            .build();

        userRepository.save(user);
        cardRepository.saveAll(List.of(card1, card2, card3));

        // when
        cardRepository.deleteAllByUser_Id(1L);
        List<Card> actual = cardRepository.findByUser_Id(1L);

        // then
        Assertions.assertThat(actual).hasSize(0);
     }
}