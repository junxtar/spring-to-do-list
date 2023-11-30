package com.sparta.springtodolist.domain.user.regex;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RegexTest {

    static final String USER_NAME_REGEXP = "^[a-z0-9]{4,10}$";
    static final String USER_PASSWORD_REGEXP = "^[a-zA-Z0-9]{8,10}$";

    @DisplayName("유저 이름 길이가 정규식에 패턴에 부합하지 않아 검사에 실패한다.")
    @Test
    void regexUsernameOfLengthFailTest() {
        // given
        String username = "tom";

        // when
        boolean isActual = Pattern.matches(USER_NAME_REGEXP, username);

        // then
        Assertions.assertFalse(isActual);
    }

    @DisplayName("유저 이름 대문자가 정규식에 패턴에 부합하지 않아 검사에 실패한다.")
    @Test
    void regexUsernameOfUppercaseFailTest() {
        // given
        String username = "Test";

        // when
        boolean isActual = Pattern.matches(USER_NAME_REGEXP, username);

        // then
        Assertions.assertFalse(isActual);
    }

    @DisplayName("유저 이름이 정규식에 패턴에 부합해 성공한다.")
    @Test
    void regexUsernameSuccessTest() {
        // given
        String username = "test123";

        // when
        boolean isActual = Pattern.matches(USER_NAME_REGEXP, username);

        // then
        Assertions.assertTrue(isActual);
    }

    @DisplayName("유저 비밀번호 길이가 정규식에 패턴에 부합하지 않아 검사에 실패한다.")
    @Test
    void regexUserPasswordOfLengthFailTest() {
        // given
        String password = "test123";

        // when
        boolean isActual = Pattern.matches(USER_PASSWORD_REGEXP, password);

        // then
        Assertions.assertFalse(isActual);
    }

    @DisplayName("유저 비밀번호 특수문자가 정규식에 패턴에 부합하지 않아 검사에 실패한다.")
    @Test
    void regexUserPasswordOfSignFailTest() {
        // given
        String password = "test!!!!!";

        // when
        boolean isActual = Pattern.matches(USER_PASSWORD_REGEXP, password);

        // then
        Assertions.assertFalse(isActual);
    }

    @DisplayName("유저 비밀번호가 정규식에 부합해 성공한다.")
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
