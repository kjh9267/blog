package me.jun.member.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.common.security.JwtProvider;
import me.jun.member.application.LoginService;
import me.jun.member.application.MemberService;
import me.jun.member.application.RegisterService;
import me.jun.member.application.dto.MemberRequest;
import me.jun.member.application.dto.TokenResponse;
import me.jun.member.application.exception.DuplicatedEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

import static me.jun.member.MemberFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureWebTestClient
@SpringBootTest
public class MemberControllerTest {

    @Autowired
    private WebTestClient webTestClient;

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
    public void registerTest() throws Exception {
        given(registerService.register(any()))
                .willReturn(CompletableFuture.completedFuture(
                        memberResponse()
                ));

        webTestClient.post()
                .uri("/api/register")
                .contentType(APPLICATION_JSON)
                .body(Mono.just(memberRequest()), MemberRequest.class)
                .exchange()

                .expectStatus().is2xxSuccessful();
    }

    @Test
    void DuplicatedEmail_registerFailTest() throws Exception {
        given(registerService.register(any()))
                .willThrow(DuplicatedEmailException.class);

        String content = objectMapper.writeValueAsString(memberRequest());

        webTestClient.post()
                .uri("/api/register")
                .contentType(APPLICATION_JSON)
                .body(Mono.just(memberRequest()), MemberRequest.class)
                .exchange()

                .expectStatus().is4xxClientError();
    }

    @Test
    public void loginTest() throws Exception {
        given(loginService.login(any()))
                .willReturn(CompletableFuture.completedFuture(
                        TokenResponse.from(jwt)
                ));

        String expected = objectMapper.writeValueAsString(
                TokenResponse.from(jwt)
        );

        webTestClient.post()
                .uri("/api/login")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(Mono.just(memberRequest()), MemberRequest.class)
                .exchange()

                .expectStatus().is2xxSuccessful()
                .expectBody().json(expected);
    }

    @Test
    void leaveTest() throws Exception {
        given(memberService.retrieveMemberBy(any()))
                .willReturn(CompletableFuture.completedFuture(
                        memberResponse()
                ));

        given(memberService.deleteMember(any()))
                .willReturn(CompletableFuture.completedFuture(MEMBER_ID));

        webTestClient.delete()
                .uri("/api/leave")
                .header(AUTHORIZATION, jwt)
                .accept(APPLICATION_JSON)
                .exchange()

                .expectStatus().is2xxSuccessful();
    }
}