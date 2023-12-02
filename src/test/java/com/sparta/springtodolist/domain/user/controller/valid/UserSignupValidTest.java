package com.sparta.springtodolist.domain.user.controller.valid;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserSignupValidTest {

    static final String USER_NAME_REGEXP = "^[a-z0-9]{4,10}$";
    static final String USER_PASSWORD_REGEXP = "^[a-zA-Z0-9]{8,10}$";
    @DisplayName("유저 이름 정규식 길이 검사 실패")
    @Test
    void regexUsernameOfLengthFailTest() {
        // given
        String username = "123";

        // when
        boolean isActual = Pattern.matches(USER_NAME_REGEXP, username);

        // then
        Assertions.assertFalse(isActual);
    }

    @DisplayName("유저 이름 정규식 대문자 검사 실패")
    @Test
    void regexUsernameOfUppercaseFailTest() {
        // given
        String username = "TEST";

        // when
        boolean isActual = Pattern.matches(USER_NAME_REGEXP, username);

        // then
        Assertions.assertFalse(isActual);
    }

    @DisplayName("유저 이름 정규식 검사 성공")
    @Test
    void regexUsernameSuccessTest() {
        // given
        String username = "abc123";

        // when
        boolean isActual = Pattern.matches(USER_NAME_REGEXP, username);

        // then
        Assertions.assertTrue(isActual);
    }
    @DisplayName("유저 비밀번호 정규식 길이 검사 실패")
    @Test
    void regexUserPasswordOfLengthFailTest() {
        // given
        String password = "abc123";

        // when
        boolean isActual = Pattern.matches(USER_PASSWORD_REGEXP, password);

        // then
        Assertions.assertFalse(isActual);
    }

    @DisplayName("유저 비밀번호 정규식 특수문자 검사 실패")
    @Test
    void regexUserPasswordOfSignFailTest() {
        // given
        String password = "abc!!!!!!";

        // when
        boolean isActual = Pattern.matches(USER_PASSWORD_REGEXP, password);

        // then
        Assertions.assertFalse(isActual);
    }

    @DisplayName("유저 비밀번호 정규식 검사 성공")
    @Test
    void regexUserPasswordSuccessTest() {
        // given
        String password = "test1234";

        // when
        boolean isActual = Pattern.matches(USER_PASSWORD_REGEXP, password);

        // then
        Assertions.assertTrue(isActual);
    }
}
