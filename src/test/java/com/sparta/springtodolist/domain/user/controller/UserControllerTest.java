package com.sparta.springtodolist.domain.user.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springtodolist.common.mvc.MockSpringSecurityFilter;
import com.sparta.springtodolist.domain.user.controller.dto.request.UserSignupRequestDto;
import com.sparta.springtodolist.domain.user.entity.User;
import com.sparta.springtodolist.domain.user.service.UserService;
import com.sparta.springtodolist.global.config.WebSecurityConfig;
import com.sparta.springtodolist.global.secutiry.UserDetailsImpl;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = UserController.class, excludeFilters = {
    @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = WebSecurityConfig.class
    )
})

class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private Principal mockPrincipal;

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
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
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