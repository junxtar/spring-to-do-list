package com.sparta.springtodolist.domain.comment.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springtodolist.common.mvc.MockSpringSecurityFilter;
import com.sparta.springtodolist.domain.comment.controller.dto.request.CommentCreateRequestDto;
import com.sparta.springtodolist.domain.comment.service.CommentService;
import com.sparta.springtodolist.domain.user.entity.User;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = CommentController.class, excludeFilters = {
    @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = WebSecurityConfig.class
    )
})
public class CommentControllerTest {

    @MockBean
    CommentService commentService;

    @Autowired
    ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private Principal mockPrincipal;

    private void mockUserSetup() {
        User user1 = User.builder()
            .username("test")
            .password("test1234")
            .build();

        UserDetailsImpl testUserDetails = new UserDetailsImpl(user1);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "",
            testUserDetails.getAuthorities());
    }

    @BeforeEach
    public void setup() {
        this.mockUserSetup();

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .alwaysDo(print())
            .build();
    }

    @DisplayName("댓글을 생성한다.")
    @Test
    void createComment() throws Exception {
        // given
        long cardId = 1L;
        CommentCreateRequestDto request = CommentCreateRequestDto.builder()
            .content("test")
            .build();
        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/comments/cards/" + cardId)
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @DisplayName("댓글을 수정한다.")
    @Test
    void updateComment() throws Exception {
        // given
        long commentId = 1L;
        CommentCreateRequestDto request = CommentCreateRequestDto.builder()
            .content("update")
            .build();
        // when, then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/comments/" + commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @DisplayName("댓글을 삭제한다.")
    @Test
    void deleteComment() throws Exception {
        // given
        long commentId = 1L;

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/comments/" + commentId)
                .principal(mockPrincipal))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
