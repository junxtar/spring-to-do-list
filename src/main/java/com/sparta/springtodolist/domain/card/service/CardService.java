package com.sparta.springtodolist.domain.card.service;

import static com.sparta.springtodolist.domain.card.constant.CardConstant.DEFAULT_COMPLETE_VALUE;

import com.sparta.springtodolist.domain.card.entity.Card;
import com.sparta.springtodolist.domain.card.exception.CardNotAccessException;
import com.sparta.springtodolist.domain.card.exception.CardNotFoundException;
import com.sparta.springtodolist.domain.card.repository.CardRepository;
import com.sparta.springtodolist.domain.card.service.dto.request.CardCreateServiceRequestDto;
import com.sparta.springtodolist.domain.card.service.dto.request.CardUpdateServiceRequestDto;
import com.sparta.springtodolist.domain.card.service.dto.response.CardCompletedResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.CardCreateResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.CardPrivatedResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.CardResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.SingleCardResponseDto;
import com.sparta.springtodolist.domain.comment.service.dto.resopnse.CommentResponseDto;
import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.global.exception.ErrorCode;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;

    public SingleCardResponseDto getCard(Long cardId, User user) {
        Card card = verifyExistsCard(cardId);
        if (!card.getUser().getUsername().equals(user.getUsername()) && !card.getIsPublic()) {
            throw new CardNotAccessException(ErrorCode.CARD_NOT_ACCESS);
        }
        List<CommentResponseDto> commentList = card.getComments().stream()
            .map(CommentResponseDto::of).collect(
                Collectors.toList());

        return SingleCardResponseDto.of(card, commentList);
    }

    public HashMap<String, List<CardResponseDto>> getCardList(User user) {
        return cardRepository.findAllByOrderByCreatedAtDesc()
            .stream()
            .map(CardResponseDto::of)
            .filter(dto -> dto.getUsername().equals(user.getUsername()) || dto.getIsPublic())
            .collect(Collectors.groupingBy(CardResponseDto::getUsername, HashMap::new,
                Collectors.toList()));
    }


    public HashMap<String, List<CardResponseDto>> getNotCompletedCardList(User user) {
        return cardRepository.findAllByOrderByCreatedAtDesc()
            .stream()
            .map(CardResponseDto::of)
            .filter(dto -> dto.getUsername().equals(user.getUsername()) || dto.getIsPublic())
            .filter(card -> !card.getIsCompleted())
            .collect(Collectors.groupingBy(CardResponseDto::getUsername, HashMap::new,
                Collectors.toList()));

    }

    public List<CardResponseDto> getSearchCardList(String title, User user) {
        return cardRepository.findCardsByTitleContaining(title).stream()
            .map(CardResponseDto::of)
            .filter(dto -> dto.getUsername().equals(user.getUsername()) || dto.getIsPublic())
            .collect(Collectors.toList());
    }

    @Transactional
    public CardCreateResponseDto createCard(CardCreateServiceRequestDto requestDto,
        User user) {
        Card card = Card.builder()
            .content(requestDto.getContent())
            .title(requestDto.getTitle())
            .isCompleted(DEFAULT_COMPLETE_VALUE)
            .isPublic(requestDto.getIsPublic())
            .user(user)
            .build();

        Card saveCard = cardRepository.save(card);

        return CardCreateResponseDto.of(saveCard, user);
    }

    @Transactional
    public CardResponseDto updateCard(Long cardId, CardUpdateServiceRequestDto toServiceRequest,
        User user) {
        Card card = verifyExistsCard(cardId);

        verifyCardOwner(user, card);

        card.update(toServiceRequest.getTitle(), toServiceRequest.getContent());

        return CardResponseDto.of(card);
    }

    @Transactional
    public CardCompletedResponseDto updateCardCompleted(Long cardId, User user) {
        Card card = verifyExistsCard(cardId);
        verifyCardOwner(user, card);
        card.updateIsCompleted();
        return CardCompletedResponseDto.of(card.getIsCompleted());
    }

    @Transactional
    public CardPrivatedResponseDto updateCardPublic(Long cardId, User user) {
        Card card = verifyExistsCard(cardId);
        verifyCardOwner(user, card);
        card.updateIsPrivated();
        return CardPrivatedResponseDto.of(card.getIsPublic());
    }

    @Transactional
    public void deleteCard(Long cardId, User user) {
        Card card = verifyExistsCard(cardId);
        verifyCardOwner(user, card);

        cardRepository.delete(card);
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        cardRepository.deleteAllByUser_Id(userId);
    }

    public Card verifyExistsCard(Long cardId) {
        return cardRepository.findById(cardId)
            .orElseThrow(() -> new CardNotFoundException(ErrorCode.CARD_NOT_FOUND));
    }

    private static void verifyCardOwner(User user, Card card) {
        if (!card.getUser().getId().equals(user.getId())) {
            throw new CardNotAccessException(ErrorCode.CARD_NOT_ACCESS);
        }
    }
}
