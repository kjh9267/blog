package me.jun.core.member.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.common.security.JwtProvider;
import me.jun.core.member.application.LoginService;
import me.jun.core.member.application.MemberService;
import me.jun.core.member.application.RegisterService;
import me.jun.core.member.application.dto.TokenResponse;
import me.jun.core.member.application.exception.DuplicatedEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static me.jun.core.member.MemberFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterService registerService;

    @MockBean
    private LoginService loginService;

    @MockBean
    private MemberService memberService;

    @Autowired
    private JwtProvider jwtProvider;

    private String jwt;

    @BeforeEach
    void setUp() {
        jwt = jwtProvider.createJwt(EMAIL);
    }

    @Test
    void registerTest() throws Exception {
        String content = objectMapper.writeValueAsString(memberRegisterRequest());

        given(registerService.register(any()))
                .willReturn(memberResponse());

        mockMvc.perform(post("/api/member/register")
                        .contentType(APPLICATION_JSON)
                        .content(content)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("_links").exists())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void DuplicatedEmail_registerFailTest() throws Exception {
        given(registerService.register(any()))
                .willThrow(new DuplicatedEmailException(EMAIL));

        String content = objectMapper.writeValueAsString(memberRegisterRequest());

        mockMvc.perform(post("/api/member/register")
                        .contentType(APPLICATION_JSON)
                        .content(content)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void loginTest() throws Exception {
        given(loginService.login(any()))
                .willReturn(TokenResponse.from(jwt));

        String content = objectMapper.writeValueAsString(memberRegisterRequest());

        mockMvc.perform(post("/api/member/login")
                        .content(content)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("_links").exists())
                .andExpect(jsonPath(ACCESS_TOKEN).value(jwt));;
    }

    @Test
    void leaveTest() throws Exception {
        given(memberService.retrieveMemberBy(any()))
                .willReturn(memberResponse());

        doNothing().when(memberService)
                .deleteMember(any());

        mockMvc.perform(delete("/api/member/leave")
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, jwt))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }
}