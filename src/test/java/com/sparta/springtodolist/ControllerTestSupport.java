package com.sparta.springtodolist;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.springtodolist.domain.card.controller.CardController;
import com.sparta.springtodolist.domain.card.service.CardService;
import com.sparta.springtodolist.domain.comment.controller.CommentController;
import com.sparta.springtodolist.domain.comment.service.CommentService;
import com.sparta.springtodolist.domain.user.controller.UserController;
import com.sparta.springtodolist.domain.user.service.UserService;
import com.sparta.springtodolist.global.config.WebSecurityConfig;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers =
    {CommentController.class,
        CardController.class,
        UserController.class
    }, excludeFilters = {
    @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = WebSecurityConfig.class
    )
})
public abstract class ControllerTestSupport {

    @MockBean
    protected UserService userService;

    @MockBean
    protected CardService cardService;

    @MockBean
    protected CommentService commentService;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext context;

    protected Principal mockPrincipal;
}
