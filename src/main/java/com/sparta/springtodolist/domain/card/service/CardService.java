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
import com.sparta.springtodolist.infra.s3.util.S3Util;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;
    private final S3Util s3Util;

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

    public HashMap<String, List<CardResponseDto>> getCardList(Pageable pageable, User user) {

        return cardRepository.findAllBy(pageable)
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
        MultipartFile multipartFile,
        User user) {
        String imageName = s3Util.uploadImage(multipartFile, S3Util.IMAGE_PATH);
        String imagePath = s3Util.getImagePath(imageName, S3Util.IMAGE_PATH);

        Card card = Card.builder()
            .content(requestDto.getContent())
            .title(requestDto.getTitle())
            .isCompleted(DEFAULT_COMPLETE_VALUE)
            .isPublic(requestDto.getIsPublic())
            .imagePath(imagePath)
            .imageName(imageName)
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
        card.updateIsPublic();
        return CardPrivatedResponseDto.of(card.getIsPublic());
    }

    @Transactional
    public void deleteCard(Long cardId, User user) {
        Card card = verifyExistsCard(cardId);
        verifyCardOwner(user, card);

        s3Util.deleteImage(card.getImageName(), S3Util.IMAGE_PATH);
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
