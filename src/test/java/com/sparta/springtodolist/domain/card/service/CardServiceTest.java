package com.sparta.springtodolist.domain.card.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sparta.springtodolist.domain.card.controller.dto.request.CardCreateRequestDto;
import com.sparta.springtodolist.domain.card.controller.dto.request.CardUpdateRequestDto;
import com.sparta.springtodolist.domain.card.entity.Card;
import com.sparta.springtodolist.domain.card.exception.CardNotAccessException;
import com.sparta.springtodolist.domain.card.exception.CardNotFoundException;
import com.sparta.springtodolist.domain.card.repository.CardRepository;
import com.sparta.springtodolist.domain.card.service.dto.response.CardCompletedResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.CardCreateResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.CardPrivatedResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.CardResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.SingleCardResponseDto;
import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.global.exception.ErrorCode;
import com.sparta.springtodolist.infra.s3.util.S3Util;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private S3Util s3Util;

    @InjectMocks
    private CardService cardService;


    @DisplayName("존재하지 않는 카드를 조회한다.")
    @Test
    void getExistsCard() {
        // given
        User user1 = User.builder()
            .id(1L)
            .username("test1")
            .password("test1234")
            .build();
        Long testId = 1L;

        given(cardRepository.findById(anyLong())).willReturn(
            Optional.empty());

        // when, then
        Assertions.assertThatThrownBy(() -> cardService.getCard(testId, user1)).isInstanceOf(
            CardNotFoundException.class).hasMessage(ErrorCode.CARD_NOT_FOUND.getMessage());
    }

    @DisplayName("해당 카드를 가지지 않는 유저가 비공개 카드를 조회한다.")
    @Test
    void getNotAccessCard() {
        // given
        User user1 = User.builder()
            .username("test1")
            .password("test1234")
            .build();

        ReflectionTestUtils.setField(user1, "id", 1L);

        User user2 = User.builder()
            .id(2L)
            .username("test2")
            .password("test1234")
            .build();

        Card card1 = Card.builder()
            .id(1L)
            .title("test1")
            .content("testContent1")
            .user(user1)
            .isCompleted(false)
            .isPublic(false)
            .build();

        Long testId = 1L;

        given(cardRepository.findById(anyLong())).willReturn(Optional.ofNullable(card1));

        // when, then
        Assertions.assertThatThrownBy(() -> cardService.getCard(testId, user2)).isInstanceOf(
            CardNotAccessException.class).hasMessage(ErrorCode.CARD_NOT_ACCESS.getMessage());
    }

    @DisplayName("단일 카드를 조회한다.")
    @Test
    void getCard() {
        // given
        User user1 = User.builder()
            .id(1L)
            .username("test1")
            .password("test1234")
            .build();

        Card card1 = Card.builder()
            .id(1L)
            .title("test1")
            .content("testContent1")
            .user(user1)
            .isCompleted(false)
            .isPublic(false)
            .build();

        Long testId = 1L;

        given(cardRepository.findById(anyLong())).willReturn(Optional.ofNullable(card1));

        // when
        SingleCardResponseDto actual = cardService.getCard(testId, user1);

        // then
        assertThat(actual).extracting("title", "content", "isCompleted", "isPublic")
            .contains("test1", "testContent1", false, false);
    }

    @DisplayName("자신의 비공개 카드를 포함해 전체 카드를 조회한다.")
    @Test
    void getCardList() {
        // given
        User user1 = User.builder()
            .id(1L)
            .username("test1")
            .password("test1234")
            .build();

        User user2 = User.builder()
            .id(2L)
            .username("test2")
            .password("test1234")
            .build();

        Card card1 = Card.builder()
            .id(1L)
            .title("test1")
            .content("testContent1")
            .user(user1)
            .isCompleted(false)
            .isPublic(false)
            .build();

        Card card2 = Card.builder()
            .id(2L)
            .title("test2")
            .content("testContent2")
            .user(user2)
            .isCompleted(true)
            .isPublic(true)
            .build();

        PageRequest pageRequest = PageRequest.of(1, 1);

        given(cardRepository.findAllBy(any())).willReturn(List.of(card1, card2));

        // when
        HashMap<String, List<CardResponseDto>> actual = cardService.getCardList(pageRequest, user1);

        // then
        assertThat(actual.get(user1.getUsername())).hasSize(1);
        assertThat(actual.get(user1.getUsername())).extracting(
            "title", "content", "username", "isCompleted", "isPublic"
        ).containsExactlyInAnyOrder(
            tuple("test1", "testContent1", "test1", false, false));
    }

    @DisplayName("다른 사용자의 비공개 카드를 제외한 전체 카드를 조회한다.")
    @Test
    void getNotPublicCardList() {
        // given
        User user1 = User.builder()
            .id(1L)
            .username("test1")
            .password("test1234")
            .build();

        User user2 = User.builder()
            .id(2L)
            .username("test2")
            .password("test1234")
            .build();

        Card card1 = Card.builder()
            .id(1L)
            .title("test1")
            .content("testContent1")
            .user(user1)
            .isCompleted(false)
            .isPublic(false)
            .build();

        Card card2 = Card.builder()
            .id(2L)
            .title("test2")
            .content("testContent2")
            .user(user2)
            .isCompleted(true)
            .isPublic(true)
            .build();

        int page = 1;
        int size = 1;
        String sortBy = "created_at";
        boolean isAsc = false;
        PageRequest pageRequest = PageRequest.of(1, 1);
        given(cardRepository.findAllBy(any())).willReturn(List.of(card1, card2));

        // when
        HashMap<String, List<CardResponseDto>> actual = cardService.getCardList(pageRequest, user1);

        // then
        assertThat(actual.get(user1.getUsername())).hasSize(1);
        assertThat(actual.get(user1.getUsername())).extracting(
            "title", "content", "username", "isCompleted", "isPublic"
        ).contains(
            tuple("test1", "testContent1", "test1", false, false)
        );
    }

    @DisplayName("완료되지 않은 카드를 조회한다.")
    @Test
    void getNotCompletedCardList() {
        // given
        User user1 = User.builder()
            .id(1L)
            .username("test1")
            .password("test1234")
            .build();

        User user2 = User.builder()
            .id(2L)
            .username("test2")
            .password("test1234")
            .build();

        Card card1 = Card.builder()
            .id(1L)
            .title("test1")
            .content("testContent1")
            .user(user1)
            .isCompleted(false)
            .isPublic(false)
            .build();

        Card card2 = Card.builder()
            .id(2L)
            .title("test2")
            .content("testContent2")
            .user(user2)
            .isCompleted(true)
            .isPublic(true)
            .build();

        given(cardRepository.findAllByOrderByCreatedAtDesc()).willReturn(List.of(card1, card2));

        // when
        HashMap<String, List<CardResponseDto>> actual = cardService.getNotCompletedCardList(
            user1);

        // then
        assertThat(actual).hasSize(1);
        assertThat(actual.get(user1.getUsername())).extracting(
            "title", "content", "username", "isCompleted", "isPublic"
        ).contains(
            tuple("test1", "testContent1", "test1", false, false)
        );
    }

    @DisplayName("검색 키워드로 카드를 조회한다.")
    @Test
    void getSearchCardList() {
        // given
        User user1 = User.builder()
            .id(1L)
            .username("test1")
            .password("test1234")
            .build();

        User user2 = User.builder()
            .id(2L)
            .username("test2")
            .password("test1234")
            .build();

        Card card1 = Card.builder()
            .id(1L)
            .title("test1")
            .content("testContent1")
            .user(user1)
            .isCompleted(false)
            .isPublic(false)
            .build();

        Card card2 = Card.builder()
            .id(2L)
            .title("test2")
            .content("testContent2")
            .user(user2)
            .isCompleted(true)
            .isPublic(true)
            .build();

        given(cardRepository.findCardsByTitleContaining(anyString())).willReturn(
            List.of(card1, card2));

        // when
        List<CardResponseDto> actual = cardService.getSearchCardList("test", user1);

        // then
        assertThat(actual).hasSize(2);
        assertThat(actual).extracting(
            "title", "content", "username", "isCompleted", "isPublic"
        ).contains(
            tuple("test1", "testContent1", "test1", false, false),
            tuple("test2", "testContent2", "test2", true, true)
        );
    }

    @DisplayName("카드를 생성한다.")
    @Test
    void createCard() throws IOException {
        // given
        User user1 = User.builder()
            .id(1L)
            .username("test1")
            .password("test1234")
            .build();

        CardCreateRequestDto request = CardCreateRequestDto.builder()
            .title("test3")
            .content("testContent3")
            .build();

        Card card = Card.builder()
            .title("test3")
            .content("testContent3")
            .user(user1)
            .build();

        given(s3Util.uploadImage(any(), anyString())).willReturn("test");
        given(cardRepository.save(any(Card.class))).willReturn(card);

        // when
        CardCreateResponseDto actual = cardService.createCard(request.toServiceRequest(), any(), user1);

        // then
        assertThat(actual.getUsername()).isEqualTo(user1.getUsername());
    }

    @DisplayName("자신의 카드를 수정한다.")
    @Test
    void updateCard() {
        // given
        User user1 = User.builder()
            .id(1L)
            .username("test1")
            .password("test1234")
            .build();

        Card card1 = Card.builder()
            .id(1L)
            .title("test1")
            .content("testContent1")
            .user(user1)
            .isCompleted(false)
            .isPublic(false)
            .build();

        CardUpdateRequestDto requestDto = CardUpdateRequestDto.builder()
            .title("update1")
            .content("updateContent1")
            .build();

        Long testId = 1L;

        given(cardRepository.findById(anyLong())).willReturn(Optional.ofNullable(card1));
        // when
        CardResponseDto actual = cardService.updateCard(testId, requestDto.toServiceRequest(),
            user1);
        // then
        assertThat(actual).extracting("title", "content")
            .contains("update1", "updateContent1");
    }

    @DisplayName("자신의 카드가 아닌 다른 사용자의 카드를 수정한다.")
    @Test
    void updateSomeoneElseCard() {
        // given
        User user1 = User.builder()
            .id(1L)
            .username("test1")
            .password("test1234")
            .build();

        User user2 = User.builder()
            .id(2L)
            .username("test2")
            .password("test1234")
            .build();

        Card card1 = Card.builder()
            .id(1L)
            .title("test1")
            .content("testContent1")
            .user(user1)
            .isCompleted(false)
            .isPublic(false)
            .build();


        CardUpdateRequestDto requestDto = CardUpdateRequestDto.builder()
            .title("update1")
            .content("updateContent1")
            .build();

        Long testId = 1L;

        given(cardRepository.findById(anyLong())).willReturn(Optional.ofNullable(card1));
        // when, then
        assertThatThrownBy(() -> cardService.updateCard(testId, requestDto.toServiceRequest(), user2))
            .isInstanceOf(CardNotAccessException.class)
            .hasMessage(ErrorCode.CARD_NOT_ACCESS.getMessage());
    }

    @DisplayName("카드의 완료 여부를 변경한다.")
    @Test
    void updateCardCompleted() {
        // given
        User user1 = User.builder()
            .id(1L)
            .username("test1")
            .password("test1234")
            .build();

        Card card1 = Card.builder()
            .id(1L)
            .title("test1")
            .content("testContent1")
            .user(user1)
            .isCompleted(false)
            .isPublic(false)
            .build();

        Long testId = 1L;

        given(cardRepository.findById(anyLong())).willReturn(Optional.ofNullable(card1));

        // when
        CardCompletedResponseDto actual = cardService.updateCardCompleted(testId, user1);

        // then
        assertThat(actual.getIsCompleted()).isTrue();
     }

    @DisplayName("카드의 공개 여부를 변경한다.")
    @Test
    void updateCardPublic() {
        // given
        User user1 = User.builder()
            .id(1L)
            .username("test1")
            .password("test1234")
            .build();

        Card card1 = Card.builder()
            .id(1L)
            .title("test1")
            .content("testContent1")
            .user(user1)
            .isCompleted(false)
            .isPublic(false)
            .build();

        Long testId = 1L;

        given(cardRepository.findById(anyLong())).willReturn(Optional.ofNullable(card1));

        // when
        CardPrivatedResponseDto actual = cardService.updateCardPublic(testId, user1);

        // then
        assertThat(actual.getIsPublic()).isTrue();
    }

    @DisplayName("카드를 삭제한다.")
    @Test
    void deleteCard() {
        // given
        User user1 = User.builder()
            .id(1L)
            .username("test1")
            .password("test1234")
            .build();

        Card card1 = Card.builder()
            .id(1L)
            .title("test1")
            .content("testContent1")
            .user(user1)
            .isCompleted(false)
            .isPublic(false)
            .build();

        Long testId = 1L;

        given(cardRepository.findById(anyLong())).willReturn(Optional.ofNullable(card1));

        // when
        cardService.deleteCard(testId, user1);

        // then
        verify(cardRepository, times(1)).delete(any(Card.class));
     }

     @DisplayName("유저의 카드를 삭제한다.")
     @Test
     void deleteByUserId() {
         // given
         Long testId = 1L;

         // when
         cardService.deleteByUserId(testId);

         // then
         verify(cardRepository, times(1)).deleteAllByUser_Id(anyLong());
      }
}