package com.sparta.springtodolist.domain.card.controller;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springtodolist.common.mvc.MockSpringSecurityFilter;
import com.sparta.springtodolist.domain.card.controller.dto.request.CardCreateRequestDto;
import com.sparta.springtodolist.domain.card.controller.dto.request.CardUpdateRequestDto;
import com.sparta.springtodolist.domain.card.service.CardService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = CardController.class, excludeFilters = {
    @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = WebSecurityConfig.class
    )
})
public class CardControllerTest {

    @MockBean
    CardService cardService;

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

    @DisplayName("단일 카드를 조회한다.")
    @Test
    void getCard() throws Exception {
        // when, then
        mockMvc.perform(get("/api/cards/" + 1L)
                .principal(mockPrincipal))
            .andExpect(status().isOk());
    }

    @DisplayName("전체 카드 리스트를 조회한다.")
    @Test
    void getCardList() throws Exception {
        // when, then
        mockMvc.perform(get("/api/cards")
                .principal(mockPrincipal))
            .andExpect(status().isOk());

    }

    @DisplayName("완료 되지 않은 전체 카드 리스트를 조회한다.")
    @Test
    void getNotCompletedCardList() throws Exception {
        // when, then
        mockMvc.perform(get("/api/cards/complete/hidden")
                .principal(mockPrincipal))
            .andExpect(status().isOk());

    }

    @DisplayName("검색 키워드로 카드 리스트를 검색한다.")
    @Test
    void getSearchCardList() throws Exception {
        // when, then
        mockMvc.perform(get("/api/cards/search")
                .param("title", "test")
                .principal(mockPrincipal))
            .andExpect(status().isOk());

    }

    @DisplayName("검색 키워드가 없이 카드 리스트를 검색한다.")
    @Test
    void getNotKeywordSearchCardList() throws Exception {
        // when, then
        mockMvc.perform(get("/api/cards/search")
                .principal(mockPrincipal))
            .andExpect(status().isBadRequest());
    }

    @DisplayName("카드를 생성한다.")
    @Test
    void createCard() throws Exception {
        // given
        CardCreateRequestDto request = CardCreateRequestDto.builder()
            .title("test")
            .content("testContent")
            .isPublic(true)
            .build();

        // when, then
        mockMvc.perform(
            post("/api/cards")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
        ).andExpect(status().isCreated());
    }

    @DisplayName("카드를 수정한다.")
    @Test
    void updateCard() throws Exception {
        // given
        CardUpdateRequestDto request = CardUpdateRequestDto.builder()
            .title("test")
            .content("testContent")
            .build();

        // when, then
        mockMvc.perform(
            put("/api/cards/" + 1L)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
        ).andExpect(status().isOk());
    }

    @DisplayName("카드의 완료 여부를 변경한다.")
    @Test
    void updateCardCompleted() throws Exception {
        // when, then
        mockMvc.perform(
            patch("/api/cards/" + 1L + "/complete")
                .principal(mockPrincipal)
        ).andExpect(status().isOk());
    }

    @DisplayName("카드의 공개 여부를 변경한다.")
    @Test
    void updateCardPublic() throws Exception {
        // when, then
        mockMvc.perform(
            patch("/api/cards/" + 1L + "/public")
                .principal(mockPrincipal)
        ).andExpect(status().isOk());
    }

    @DisplayName("카드를 삭제한다..")
    @Test
    void deleteCard() throws Exception {
        // when, then
        mockMvc.perform(
            delete("/api/cards/" + 1L)
                .principal(mockPrincipal)
        ).andExpect(status().isNoContent());
    }
}