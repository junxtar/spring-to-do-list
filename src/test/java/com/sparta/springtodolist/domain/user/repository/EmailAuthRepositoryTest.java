package com.sparta.springtodolist.domain.user.repository;

import com.sparta.springtodolist.domain.user.entity.EmailAuth;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.NONE)
class EmailAuthRepositoryTest {

    @Autowired
    private EmailAuthRepository emailAuthRepository;

    @DisplayName("emailAuth가 저장되는지 확인한다.")
    @Test
    void save() {
        // given
        EmailAuth emailAuth = EmailAuth.builder()
            .email("test@naver.com")
            .code("1234")
            .build();

        // when
        EmailAuth actual = emailAuthRepository.save(emailAuth);

        // then
        Assertions.assertEquals(actual.getCode(), "1234");
    }

    @DisplayName("emailAuth를 조회한다.")
    @Test
    void findById() {
        // given
        EmailAuth emailAuth = EmailAuth.builder()
            .email("test@naver.com")
            .code("1234")
            .build();

        emailAuthRepository.save(emailAuth);

        // when
        EmailAuth actual = emailAuthRepository.findById(emailAuth.getEmail()).get();

        // then
        Assertions.assertEquals(actual.getCode(), emailAuth.getCode());
     }
}