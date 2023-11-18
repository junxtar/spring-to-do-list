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

    public CardResponseDto getCard(Long cardId, User user) {
        Card card = verifyExistsCard(cardId);

        return CardResponseDto.of(card, user);
    }

    @Transactional
    public CardCreateResponseDto createCard(CardCreateServiceRequestDto requestDto,
        User user) {
        Card card = Card.builder()
            .content(requestDto.getContent())
            .title(requestDto.getTitle())
            .isCompleted(DEFAULT_COMPLETE_VALUE)
            .isPrivated(requestDto.getIsPrivated())
            .user(user)
            .build();

        Card saveCard = cardRepository.save(card);

        return CardCreateResponseDto.of(saveCard, user);
    }

    public HashMap<String, List<CardResponseDto>> getCardList() {
        return cardRepository.findAllByOrderByCreatedAtDesc().stream()
            .map(card -> CardResponseDto.of(card, card.getUser()))
            .collect(Collectors.groupingBy(CardResponseDto::getUsername, HashMap::new,
                Collectors.toList()));
    }

    @Transactional
    public CardResponseDto updateCard(Long cardId, CardUpdateServiceRequestDto toServiceRequest,
        User user) {
        Card card = verifyExistsCard(cardId);

        verifyCardOwner(user, card);

        card.update(toServiceRequest);

        return CardResponseDto.of(card, user);
    }

    @Transactional
    public CardCompletedResponseDto updateCardCompleted(Long cardId, User user) {
        Card card = verifyExistsCard(cardId);
        verifyCardOwner(user, card);
        card.updateIsCompleted();
        return CardCompletedResponseDto.of(card.getIsCompleted());
    }

    @Transactional
    public CardPrivatedResponseDto updateCardPrivated(Long cardId, User user) {
        Card card = verifyExistsCard(cardId);
        verifyCardOwner(user, card);
        card.updateIsPrivated();
        return CardPrivatedResponseDto.of(card.getIsPrivated());
    }

    @Transactional
    public void deleteCard(Long cardId, User user) {
        Card card = verifyExistsCard(cardId);
        verifyCardOwner(user, card);

        cardRepository.delete(card);
    }

    private Card verifyExistsCard(Long cardId) {
        return cardRepository.findById(cardId)
            .orElseThrow(() -> new CardNotFoundException(ErrorCode.CARD_NOT_FOUND));
    }

    private static void verifyCardOwner(User user, Card card) {
        if (!card.getUser().getId().equals(user.getId())) {
            throw new CardNotAccessException(ErrorCode.CARD_NOT_ACCESS);
        }
    }
}
