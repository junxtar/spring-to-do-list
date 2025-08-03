package com.sparta.springtodolist.domain.user.controller;

import com.sparta.springtodolist.domain.user.controller.dto.request.UserSendMailRequestDto;
import com.sparta.springtodolist.domain.user.controller.dto.request.UserSignupRequestDto;
import com.sparta.springtodolist.domain.user.service.UserService;
import com.sparta.springtodolist.domain.user.service.dto.response.UserSignupResponseDto;
import com.sparta.springtodolist.global.dto.ApiResponse;
import com.sparta.springtodolist.global.secutiry.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<UserSignupResponseDto> signup(
        @Valid @RequestBody UserSignupRequestDto userSignupRequestDto) {

        return ApiResponse.success(userService.signup(userSignupRequestDto.toServiceRequest()));
    }

    @GetMapping("/signup/name")
    public ResponseEntity<Void> nameCheck(@RequestParam String username) {
        userService.nameCheck(username);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signup/mail")
    public ResponseEntity<Void> sendMail(@RequestBody UserSendMailRequestDto requestDto) {
        userService.sendMail(requestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        userService.deleteUser(userDetails.getUser());

        return ResponseEntity.noContent().build();
    }
}
