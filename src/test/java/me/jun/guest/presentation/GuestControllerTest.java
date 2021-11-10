package me.jun.guest.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guest.application.GuestService;
import me.jun.guest.application.LoginService;
import me.jun.guest.application.RegisterService;
import me.jun.guest.application.exception.DuplicatedEmailException;
import me.jun.guest.application.dto.TokenResponse;
import me.jun.common.security.JwtProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static me.jun.guest.GuestFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @MockBean
    private GuestService guestService;

    @MockBean
    private JwtProvider jwtProvider;

    @Test
    public void registerTest() throws Exception {
        String content = objectMapper.writeValueAsString(guestRequest());

        given(registerService.register(any()))
                .willReturn(guestResponse());

        mockMvc.perform(post("/api/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
                    .accept(HAL_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath(LINKS_REGISTER_HREF).value(REGISTER_SELF_URI))
        .andExpect(jsonPath(LINKS_REGISTER_HREF).value(REGISTER_SELF_URI))
        .andExpect(jsonPath(LINKS_LOGIN_HREF).value(LOGIN_SELF_URI));
    }

    @Test
    void DuplicatedEmail_registerFailTest() throws Exception {
        given(registerService.register(any()))
                .willThrow(DuplicatedEmailException.class);

        String content = objectMapper.writeValueAsString(guestRequest());

        mockMvc.perform(post("/api/register")
                .content(content)
                .accept(HAL_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void loginTest() throws Exception {
        given(loginService.login(any()))
                .willReturn(TokenResponse.from(JWT));

        String content = objectMapper.writeValueAsString(guestRequest());

        mockMvc.perform(post("/api/login")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(HAL_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", LOGIN_SELF_URI))
                .andExpect(jsonPath(LINKS_SELF_HREF).value(LOGIN_SELF_URI))
                .andExpect(jsonPath(LINKS_REGISTER_HREF).value(REGISTER_SELF_URI))
                .andExpect(jsonPath(ACCESS_TOKEN).value(JWT));
    }

    @Test
    void leaveTest() throws Exception {
        given(jwtProvider.extractSubject(any()))
                .willReturn(EMAIL);

        doNothing().when(jwtProvider)
                .validateToken(any());

        given(guestService.retrieveGuestBy(any()))
                .willReturn(guestResponse());

        doNothing().when(guestService)
                .deleteGuest(any());

        mockMvc.perform(delete("/api/leave")
                .accept(HAL_JSON)
                .header(AUTHORIZATION, JWT))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath(LINKS_LEAVE_HREF).value(LEAVE_SELF_URI))
                .andExpect(jsonPath(LINKS_LOGIN_HREF).value(LOGIN_SELF_URI))
                .andExpect(jsonPath(LINKS_REGISTER_HREF).value(REGISTER_SELF_URI));
    }
}