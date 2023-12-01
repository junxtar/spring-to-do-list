package com.sparta.springtodolist.common.encode;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class EncodeTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("비밀번호 암호화를 검증한다.")
    @Test
    void encodePassword() {
        // given
        String originPassword = "test1234";
        String encodePassword = passwordEncoder.encode(originPassword);

        // when
        boolean actual = passwordEncoder.matches(originPassword, encodePassword);

        // then
        Assertions.assertTrue(actual);
     }
}
