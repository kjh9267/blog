package me.jun.guestbook.guest.application;

import me.jun.guestbook.guest.presentation.dto.GuestRequest;
import me.jun.guestbook.guest.presentation.dto.GuestResponse;
import me.jun.guestbook.guest.presentation.dto.TokenResponse;
import me.jun.guestbook.guest.application.exception.DuplicatedEmailException;
import me.jun.guestbook.guest.application.exception.EmailNotFoundException;
import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
import me.jun.guestbook.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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

    private Guest guest;

    private GuestRequest guestRequest;

    private final String name = "testuser";

    private final String email = "testuser@email.com";

    private final String password = "pass";

    @BeforeEach
    public void setUp() {
        loginService = new LoginService(guestRepository, jwtProvider);

        guest = Guest.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        guestRequest = GuestRequest.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();
    }

    @Test
    void loginTest() {
        given(jwtProvider.createJwt(any())).willReturn("1.2.3");
        given(guestRepository.findByEmail(any())).willReturn(Optional.of(guest));

        assertThat(loginService.login(guestRequest))
                .isEqualTo(TokenResponse.from("1.2.3"));
    }

    @Test
    void NoGuest_loginFailTest() {
        given(guestRepository.findByEmail(email)).willReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class,
                () -> loginService.login(guestRequest)
        );
    }
}
