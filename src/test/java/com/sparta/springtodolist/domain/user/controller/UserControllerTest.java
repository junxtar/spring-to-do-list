package com.sparta.springtodolist.domain.user.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sparta.springtodolist.ControllerTestSupport;
import com.sparta.springtodolist.common.mvc.MockSpringSecurityFilter;
import com.sparta.springtodolist.domain.user.controller.dto.request.UserSignupRequestDto;
import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.global.secutiry.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class UserControllerTest extends ControllerTestSupport {

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .alwaysDo(print())
            .build();
    }

    private void mockUserSetup() {
        User testUser = User.builder()
            .username("test")
            .password("test1234")
            .build();

        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "",
            testUserDetails.getAuthorities());
    }

    @DisplayName("사용자가 회원가입을 한다.")
    @Test
    void signup() throws Exception {
        // given
        UserSignupRequestDto request = UserSignupRequestDto.builder()
            .username("test")
            .password("test1234")
            .build();

        // when, then
        mockMvc.perform(post("/api/users/signup")
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @DisplayName("사용자가 서비스에서 탈퇴한다.")
    @Test
    void deleteUser() throws Exception {
        // given
        this.mockUserSetup();

        // when, then
        mockMvc.perform(delete("/api/users")
            .principal(mockPrincipal)
        ).andExpect(status().isNoContent());
    }
}