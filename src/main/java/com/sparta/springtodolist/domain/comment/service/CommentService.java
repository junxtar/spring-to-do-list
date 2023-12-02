package com.sparta.springtodolist.domain.comment.service;

import com.sparta.springtodolist.domain.card.entity.Card;
import com.sparta.springtodolist.domain.card.service.CardService;
import com.sparta.springtodolist.domain.comment.entity.Comment;
import com.sparta.springtodolist.domain.comment.exception.CommentNotAccessException;
import com.sparta.springtodolist.domain.comment.exception.CommentNotFoundException;
import com.sparta.springtodolist.domain.comment.repository.CommentRepository;
import com.sparta.springtodolist.domain.comment.service.dto.request.CommentCreateServiceRequestDto;
import com.sparta.springtodolist.domain.comment.service.dto.request.CommentUpdateServiceRequestDto;
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
    private final CardService cardService;

    @Transactional
    public CommentResponseDto createComment(Long cardId,
        CommentCreateServiceRequestDto toServiceRequest, User user) {
        Card card = cardService.verifyExistsCard(cardId);

        Comment comment = Comment.builder()
            .content(toServiceRequest.getContent())
            .card(card)
            .user(user)
            .build();

        Comment saveComment = commentRepository.save(comment); 

        return CommentResponseDto.of(saveComment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId,
        CommentUpdateServiceRequestDto toServiceRequest, User user) {
        Comment comment = verifyExistsComment(commentId);
        verifyCommentOwner(user, comment);

        comment.update(toServiceRequest.getContent());

        return CommentResponseDto.of(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = verifyExistsComment(commentId);
        verifyCommentOwner(user, comment);

        commentRepository.delete(comment);
    }

    private Comment verifyExistsComment(Long commentId) {
        return commentRepository.findById(commentId)
            .orElseThrow(() -> new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
    }

    private static void verifyCommentOwner(User user, Comment comment) {
        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CommentNotAccessException(ErrorCode.COMMENT_NOT_ACCESS);
        }
    }
}
