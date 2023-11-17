package com.sparta.springtodolist.domain.user.controller;

import com.sparta.springtodolist.domain.user.controller.dto.request.UserSignupRequestDto;
import com.sparta.springtodolist.domain.user.service.UserService;
import com.sparta.springtodolist.domain.user.service.dto.response.UserSignupResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<UserSignupResponseDto> signup(
        @Validated @RequestBody UserSignupRequestDto userSignupRequestDto) {

        return ResponseEntity.ok(userService.signup(userSignupRequestDto.toServiceRequest()));
    }
}