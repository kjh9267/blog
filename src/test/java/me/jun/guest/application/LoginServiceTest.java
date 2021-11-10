package me.jun.guest.application;

import me.jun.guest.application.exception.EmailNotFoundException;
import me.jun.guest.domain.repository.GuestRepository;
import me.jun.common.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static me.jun.guest.GuestFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    private LoginService loginService;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private JwtProvider jwtProvider;

    @BeforeEach
    public void setUp() {
        loginService = new LoginService(guestRepository, jwtProvider);
    }

    @Test
    void loginTest() {
        given(jwtProvider.createJwt(any())).willReturn(JWT);
        given(guestRepository.findByEmail(any())).willReturn(Optional.of(guest()));

        assertThat(loginService.login(guestRequest()))
                .isEqualTo(tokenResponse());
    }

    @Test
    void NoGuest_loginFailTest() {
        given(guestRepository.findByEmail(EMAIL)).willReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class,
                () -> loginService.login(guestRequest())
        );
    }
}
