package com.sparta.springtodolist.domain.comment.service;

import com.sparta.springtodolist.domain.card.entity.Card;
import com.sparta.springtodolist.domain.card.exception.CardNotAccessException;
import com.sparta.springtodolist.domain.card.exception.CardNotFoundException;
import com.sparta.springtodolist.domain.card.repository.CardRepository;
import com.sparta.springtodolist.domain.comment.entity.Comment;
import com.sparta.springtodolist.domain.comment.repository.CommentRepository;
import com.sparta.springtodolist.domain.comment.service.dto.request.CommentCreateServiceRequestDto;
import com.sparta.springtodolist.domain.comment.service.dto.resopnse.CommentResponseDto;
import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;

    @Transactional
    public CommentResponseDto createComment(Long cardId,
        CommentCreateServiceRequestDto toServiceRequest, User user) {
        Card card = verifyExistsCard(cardId);

        Comment comment = Comment.builder()
            .content(toServiceRequest.getContent())
            .card(card)
            .build();

        Comment saveComment = commentRepository.save(comment);

        return CommentResponseDto.of(saveComment);
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
