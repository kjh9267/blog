package me.jun.guestbook.guest.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.guest.application.LoginService;
import me.jun.guestbook.guest.application.RegisterService;
import me.jun.guestbook.guest.presentation.dto.GuestRequest;
import me.jun.guestbook.guest.presentation.dto.GuestResponse;
import me.jun.guestbook.guest.presentation.dto.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static me.jun.guestbook.guest.presentation.GuestControllerUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterService registerService;

    @MockBean
    private LoginService loginService;

    private GuestRequest request;

    private GuestResponse response;

    @BeforeEach
    void setUp() {
        request = GuestRequest.builder()
                .name("jun")
                .email("testuser@email.com")
                .password("pass")
                .build();

        response = GuestResponse.builder()
                .id(1L)
                .email("testuser@email.com")
                .name("jun")
                .build();
    }

    @Test
    public void registerTest() throws Exception {
        String content = objectMapper.writeValueAsString(request);

        given(registerService.register(any()))
                .willReturn(response);

        mockMvc.perform(post("/api/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
                    .accept(HAL_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath(LINKS_SELF_HREF).value(REGISTER_SELF_URI))
        .andExpect(jsonPath(LINKS_LOGIN_HREF).value(LOGIN_SELF_URI));
    }

    @Test
    public void loginTest() throws Exception {
        given(loginService.login(any()))
                .willReturn(TokenResponse.from("1.2.3"));

        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/api/login")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(HAL_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", LOGIN_SELF_URI))
                .andExpect(jsonPath(LINKS_SELF_HREF).value(LOGIN_SELF_URI))
                .andExpect(jsonPath(LINKS_REGISTER_HREF).value(REGISTER_SELF_URI))
                .andExpect(jsonPath(ACCESS_TOKEN).value("1.2.3"));
    }
}