package com.sparta.springtodolist.global.secutiry;

import static com.sparta.springtodolist.global.exception.ErrorCode.NOT_FOUND_USERNAME;
import static org.mockito.ArgumentMatchers.anyString;

import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.domain.user.repository.UserRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @DisplayName("존재하지 않는 사용자가 우리 서비스에 존재하는 사용자인지 검증한다.")
    @Test
    void loadNotExistsUserByUsername() {
        // given
        BDDMockito.given(userRepository.findByUsername(anyString())).willReturn(Optional.empty());
        String username = "test";

        // when, then
        Assertions.assertThatThrownBy(() -> userDetailsService.loadUserByUsername(username))
            .isInstanceOf(UsernameNotFoundException.class).hasMessage(NOT_FOUND_USERNAME.getMessage());
    }

    @DisplayName("존재하는 사용자가 우리 서비스에 존재하는 사용자인지 검증한다.")
    @Test
    void loadUserByUsername() {
        // given
        User user = User.builder()
            .username("test")
            .password("test1234")
            .build();

        BDDMockito.given(userRepository.findByUsername(anyString())).willReturn(Optional.of(user));
        String username = "test";

        // when
        UserDetails actual = userDetailsService.loadUserByUsername(username);

        // then
        Assertions.assertThat(actual).extracting("username", "password")
            .contains("test", "test1234");
    }
}