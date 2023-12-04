package com.sparta.springtodolist.global.secutiry;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class JwtTokenProviderTest {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        jwtTokenProvider.init();
    }

    @DisplayName("토큰을 생성한다.")
    @Test
    void createToken() {
        // given
        String username = "test";

        // when
        String actual = jwtTokenProvider.createToken(username);

        // then
        assertNotNull(actual);
     }

    @DisplayName("클라이언트 요청 헤더에서 Authorization 의 토큰을 가져온다.")
    @Test
    void getTokenFromHeader() {
        // given
        String token = "Bearer token";
        given(request.getHeader(anyString())).willReturn(token);

        // when
        String actual = jwtTokenProvider.getJwtFromHeader(request);

        // then
        assertThat(actual).isEqualTo("token");
    }

    @DisplayName("토큰 검증")
    @Nested
    class validateToken {

        @DisplayName("토큰을 검증하는데 성공한다.")
        @Test
        void validateTokenSuccess() {
            // given
            String username = "test";
            String token = jwtTokenProvider.createToken(username).substring(7);

            // when
            boolean actual = jwtTokenProvider.validateToken(token);

            // then
            assertTrue(actual);
         }

        @DisplayName("토큰을 검증하는데 실패한다.")
        @Test
        void validateTokenFail() {
            // given
            String token = "invalid-token";

            // when
            boolean actual = jwtTokenProvider.validateToken(token);

            // then
            assertFalse(actual);
        }
    }
    @DisplayName("토큰에서 UserInfo 조회")
    @Test
    void getUserInfoFromToken() {
        // given
        String username = "test";
        String token = jwtTokenProvider.createToken(username).substring(7);

        // when
        Claims claims = jwtTokenProvider.getUserInfoFromToken(token);
        // then
        assertNotNull(claims);
        assertEquals(username, claims.getSubject());
    }

}