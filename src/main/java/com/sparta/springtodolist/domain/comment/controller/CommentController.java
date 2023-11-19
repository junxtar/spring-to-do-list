package com.sparta.springtodolist.domain.comment.controller;

import com.sparta.springtodolist.domain.comment.controller.dto.request.CommentCreateRequestDto;
import com.sparta.springtodolist.domain.comment.controller.dto.request.CommentUpdateRequestDto;
import com.sparta.springtodolist.domain.comment.service.CommentService;
import com.sparta.springtodolist.domain.comment.service.dto.resopnse.CommentResponseDto;
import com.sparta.springtodolist.global.secutiry.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/cards/{cardId}")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long cardId,
        @RequestBody CommentCreateRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(cardId,
            requestDto.toServiceRequest(), userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
        @RequestBody CommentUpdateRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto.toServiceRequest(), userDetails.getUser());

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(commentId, userDetails.getUser());

        return ResponseEntity.noContent().build();
    }
}
