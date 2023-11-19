package com.sparta.springtodolist.domain.user.controller;

import com.sparta.springtodolist.domain.user.controller.dto.request.UserSignupRequestDto;
import com.sparta.springtodolist.domain.user.service.UserService;
import com.sparta.springtodolist.domain.user.service.dto.response.UserSignupResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponseDto> signup(
        @Valid @RequestBody UserSignupRequestDto userSignupRequestDto) {

        return ResponseEntity.ok(userService.signup(userSignupRequestDto.toServiceRequest()));
    }
}
