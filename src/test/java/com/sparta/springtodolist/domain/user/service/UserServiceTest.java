package com.sparta.springtodolist.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.sparta.springtodolist.domain.card.service.CardService;
import com.sparta.springtodolist.domain.user.controller.dto.request.UserSignupRequestDto;
import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.domain.user.exception.ExistsUserException;
import com.sparta.springtodolist.domain.user.repository.UserRepository;
import com.sparta.springtodolist.domain.user.service.dto.response.UserSignupResponseDto;
import com.sparta.springtodolist.global.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private CardService cardService;

    @InjectMocks
    private UserService userService;

    @DisplayName("사용자가 회원 가입을 해서 성공한다.")
    @Transactional
    @Test
    void signup() {
        // given
        given(userRepository.existsByUsername(anyString())).willReturn(false);
        given(userRepository.save(any(User.class))).willReturn(
            User.builder()
                .username("test")
                .build()
        );

        UserSignupRequestDto requestDto = UserSignupRequestDto.builder()
            .username("test")
            .password("test1234")
            .build();

        // when
        UserSignupResponseDto responseDto = userService.signup(requestDto.toServiceRequest());

        // then
        assertThat(responseDto.getUsername()).isEqualTo(requestDto.getUsername());
    }

    @DisplayName("사용자가 회원 가입을 하는데 존재하는 이름이 존재한다.")
    @Transactional
    @Test
    void signupFail() {
        // given
        given(userRepository.existsByUsername(anyString())).willReturn(true);

        UserSignupRequestDto requestDto = UserSignupRequestDto.builder()
            .username("test")
            .password("test1234")
            .build();

        // when, then
        assertThatThrownBy(() -> userService.signup(requestDto.toServiceRequest())).isInstanceOf(
            ExistsUserException.class
        ).hasMessage(ErrorCode.EXISTS_USERNAME.getMessage());
    }

    @DisplayName("사용자의 탈퇴를 검증한다.")
    @Test
    void deleteUser() {
        // given
        when(userRepository.existsById(anyLong())).thenReturn(true);

        User user = User.builder()
            .id(1L)
            .username("test")
            .password("test1234")
            .build();

        // when
        userService.deleteUser(user);

        // then
        verify(userRepository, times(1)).delete(any(User.class));
    }

    @DisplayName("존재하지 않는 사용자의 탈퇴를 검증한다.")
    @Test
    void deleteNotExistsUser() {
        // given
        given(userRepository.existsById(anyLong())).willReturn(false);

        User user = User.builder()
            .id(1L)
            .username("test")
            .password("test1234")
            .build();

        // when, then
        Assertions.assertThatThrownBy(() -> userService.deleteUser(user)).isInstanceOf(
            ExistsUserException.class
        ).hasMessage(ErrorCode.EXISTS_USERNAME.getMessage());
     }

}