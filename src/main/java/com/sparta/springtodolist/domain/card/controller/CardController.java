package com.sparta.springtodolist.domain.card.controller;

import com.sparta.springtodolist.domain.card.controller.dto.request.CardCreateRequestDto;
import com.sparta.springtodolist.domain.card.controller.dto.request.CardUpdateRequestDto;
import com.sparta.springtodolist.domain.card.service.CardService;
import com.sparta.springtodolist.domain.card.service.dto.response.CardCompletedResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.CardCreateResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.CardPrivatedResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.CardResponseDto;
import com.sparta.springtodolist.domain.card.service.dto.response.SingleCardResponseDto;
import com.sparta.springtodolist.global.secutiry.UserDetailsImpl;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    @GetMapping("/{cardId}")
    public ResponseEntity<SingleCardResponseDto> getCard(@PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        SingleCardResponseDto responseDto = cardService.getCard(cardId, userDetails.getUser());

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<HashMap<String, List<CardResponseDto>>> getCardList(
        Pageable pageable,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        HashMap<String, List<CardResponseDto>> cardMap = cardService.getCardList(
            pageable,
            userDetails.getUser());

        return ResponseEntity.ok(cardMap);
    }

    @GetMapping("/complete/hidden")
    public ResponseEntity<HashMap<String, List<CardResponseDto>>> getNotCompletedCardList(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        HashMap<String, List<CardResponseDto>> cardMap = cardService.getNotCompletedCardList(
            userDetails.getUser());

        return ResponseEntity.ok(cardMap);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CardResponseDto>> getSearchCardList(@RequestParam String title,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CardResponseDto> responseDtoList = cardService.getSearchCardList(title,
            userDetails.getUser());

        return ResponseEntity.ok(responseDtoList);
    }

    @PostMapping
    public ResponseEntity<CardCreateResponseDto> createCard(
        @RequestPart("dto") CardCreateRequestDto requestDto,
        @RequestPart("file") MultipartFile multipartFile,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardCreateResponseDto responseDto = cardService.createCard(requestDto.toServiceRequest(),
            multipartFile,
            userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long cardId,
        @RequestBody CardUpdateRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardResponseDto responseDto = cardService.updateCard(cardId, requestDto.toServiceRequest(),
            userDetails.getUser());

        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{cardId}/complete")
    public ResponseEntity<CardCompletedResponseDto> updateCardCompleted(@PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardCompletedResponseDto responseDto = cardService.updateCardCompleted(cardId,
            userDetails.getUser());

        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{cardId}/public")
    public ResponseEntity<CardPrivatedResponseDto> updateCardPublic(@PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CardPrivatedResponseDto responseDto = cardService.updateCardPublic(cardId,
            userDetails.getUser());

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long cardId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cardService.deleteCard(cardId, userDetails.getUser());

        return ResponseEntity.noContent().build();
    }

}
