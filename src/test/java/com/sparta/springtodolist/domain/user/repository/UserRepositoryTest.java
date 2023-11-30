package com.sparta.springtodolist.domain.user.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sparta.springtodolist.RepositoryTestSupport;
import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.domain.user.exception.ExistsUserException;
import com.sparta.springtodolist.global.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserRepositoryTest extends RepositoryTestSupport {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
    }

    @DisplayName("사용자의 이름이 존재하는지 검증한다.")
    @Test
    void existsByUsername() {
        // given
        User user = User.builder()
            .username("test")
            .password("test1234")
            .build();

        userRepository.save(user);

        // when
        boolean actual = userRepository.existsByUsername(user.getUsername());

        // then
        assertTrue(actual);
    }

    @DisplayName("사용자의 이름으로 존재하는 사용자를 조회한다.")
    @Test
    void findByUsername() {
        // given
        User user = User.builder()
            .username("test")
            .password("test1234")
            .build();

        userRepository.save(user);

        // when
        User actual = userRepository.findByUsername(user.getUsername())
            .orElseThrow(() -> new ExistsUserException(ErrorCode.EXISTS_USERNAME));

        // then
        Assertions.assertThat(actual.getUsername()).isEqualTo(user.getUsername());
    }

    @DisplayName("사용자의 이름으로 존재하지 않는 사용자를 조회한다.")
    @Test
    void findByNotExistsUsername() {
        // given
        User user = User.builder()
            .username("test")
            .password("test1234")
            .build();

        userRepository.save(user);

        // when, then
        Assertions.assertThatThrownBy(() -> userRepository.findByUsername("test1")
                .orElseThrow(() -> new ExistsUserException(ErrorCode.EXISTS_USERNAME)))
            .isInstanceOf(ExistsUserException.class)
            .hasMessage(ErrorCode.EXISTS_USERNAME.getMessage());
    }

}