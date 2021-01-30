package me.jun.guestbook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.dto.AccountRequestDto;
import me.jun.guestbook.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    AccountService accountService;

    MockHttpSession mockHttpSession;

    @Test
    public void registerTest() throws Exception {
        final AccountRequestDto requestDto = AccountRequestDto.builder()
                .name("jun")
                .email("testuser@email.com")
                .password("pass")
                .build();

        final String content = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/register")
                    .contentType("application/json")
                    .content(content))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void loginTest() throws Exception {
        final AccountRequestDto requestDto = AccountRequestDto.builder()
                .name("jun")
                .email("testuser@email.com")
                .password("pass")
                .build();

        accountService.createAccount(requestDto);

        final String content = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/login")
                    .content(content)
                    .contentType("application/json"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void loginSessionTest() throws Exception {
        final AccountRequestDto requestDto = AccountRequestDto.builder()
                .name("jun")
                .email("testuser@email.com")
                .password("pass")
                .build();

        accountService.createAccount(requestDto);

        mockHttpSession = new MockHttpSession();

        mockHttpSession.setAttribute("login", requestDto);

        mockMvc.perform(get("/index")
                .session(mockHttpSession)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }
}