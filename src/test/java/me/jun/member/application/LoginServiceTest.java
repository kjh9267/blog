package me.jun.member.application;

import me.jun.common.security.JwtProvider;
import me.jun.member.application.exception.MemberNotFoundException;
import me.jun.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static me.jun.member.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    private LoginService loginService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtProvider jwtProvider;

    @BeforeEach
    public void setUp() {
        loginService = new LoginService(memberRepository, jwtProvider);
    }

    @Test
    void loginTest() {
        given(jwtProvider.createJwt(any()))
                .willReturn(JWT);

        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.of(member()));

        assertThat(loginService.login(memberLoginRequest()))
                .isEqualTo(tokenResponse());
    }

    @Test
    void noMember_loginFailTest() {
        given(memberRepository.findByEmail(EMAIL)).willReturn(Optional.empty());

        assertThrows(MemberNotFoundException.class,
                () -> loginService.login(memberLoginRequest())
        );
    }
}
