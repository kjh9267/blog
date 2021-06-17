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
public class GuestAuthServiceTest {

    private GuestAuthService guestAuthService;

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
        guestAuthService = new GuestAuthService(guestRepository, jwtProvider);

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

        assertThat(guestAuthService.login(guestRequest))
                .isEqualTo(TokenResponse.from("1.2.3"));
    }

    @Test
    void NoGuest_loginFailTest() {
        given(guestRepository.findByEmail(email)).willReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class,
                () -> guestAuthService.login(guestRequest)
        );
    }

    @Test
    void registerTest() {
        given(guestRepository.save(guest)).willReturn(guest);

        GuestResponse guestResponse = guestAuthService.register(guestRequest);

        assertAll(
                () -> assertThat(guestResponse).isInstanceOf(GuestResponse.class),
                () -> assertThat(guestResponse.getName()).isEqualTo(name),
                () -> assertThat(guestResponse.getEmail()).isEqualTo(email)
        );
    }

    @Test
    void registerFailTest() {
        given(guestRepository.save(guest)).willThrow(new DataIntegrityViolationException(""));

        assertThrows(DuplicatedEmailException.class,
                () -> guestAuthService.register(guestRequest)
        );
    }
}
