package com.sparta.springtodolist.domain.card.service;

import com.sparta.springtodolist.domain.card.domain.Card;
import com.sparta.springtodolist.domain.card.exception.CardNotFoundException;
import com.sparta.springtodolist.domain.card.repository.CardRepository;
import com.sparta.springtodolist.domain.card.service.dto.request.CardCreateServiceRequestDto;
import com.sparta.springtodolist.domain.card.service.dto.response.CardCreateResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.CardResponseDto;
import com.sparta.springtodolist.domain.user.domain.User;
import com.sparta.springtodolist.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;

    public CardResponseDto getCard(Long cardId, User user) {
        Card card = cardRepository.findById(cardId)
            .orElseThrow(() -> new CardNotFoundException(ErrorCode.CARD_NOT_FOUND));

        return CardResponseDto.of(card, user);
    }

    @Transactional
    public CardCreateResponseDto createCard(CardCreateServiceRequestDto requestDto,
        User user) {
        Card card = Card.builder()
            .content(requestDto.getContent())
            .title(requestDto.getTitle())
            .isCompleted(requestDto.getIsCompleted())
            .isPrivated(requestDto.getIsPrivated())
            .user(user)
            .build();

        Card saveCard = cardRepository.save(card);

        return CardCreateResponseDto.of(saveCard, user);
    }
}
