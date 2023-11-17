package com.sparta.springtodolist.domain.card.controller;

import com.sparta.springtodolist.domain.card.controller.dto.request.CardCreateRequestDto;
import com.sparta.springtodolist.domain.card.service.CardService;
import com.sparta.springtodolist.domain.card.service.dto.response.CardCreateResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.CardResponseDto;
import com.sparta.springtodolist.global.secutiry.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CardController {

    private final CardService cardService;

    @GetMapping("/card/{cardId}")
    public ResponseEntity<CardResponseDto> getCard(@PathVariable Long cardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto responseDto = cardService.getCard(cardId, userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }
    @PostMapping("/card")
    public ResponseEntity<CardCreateResponseDto> createCard(
        @Valid @RequestBody CardCreateRequestDto requestDto, @AuthenticationPrincipal
    UserDetailsImpl userDetails) {
        CardCreateResponseDto responseDto = cardService.createCard(requestDto.toServiceRequest(), userDetails.getUser());
        return ResponseEntity.ok(responseDto);
    }

}
