package me.jun.guestbook.guest.application;

import me.jun.guestbook.guest.application.exception.DuplicatedEmailException;
import me.jun.guestbook.guest.application.exception.EmailNotFoundException;
import me.jun.guestbook.guest.application.GuestService;
import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
import me.jun.guestbook.dto.GuestRequest;
import me.jun.guestbook.dto.GuestResponse;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class GuestServiceTest {

    private GuestService guestService;

    @Mock
    private GuestRepository guestRepository;

    private Guest guest;

    private GuestRequest guestRequest;

    private final String name = "testuser";

    private final String email = "testuser@email.com";

    private final String password = "pass";

    @BeforeEach
    public void setUp() {
        guestService = new GuestService(guestRepository);

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
        given(guestRepository.findByEmail(email)).willReturn(Optional.of(guest));

        // When
        GuestResponse guestResponse = guestService.login(guestRequest);

        // Then
        assertAll(
                () -> assertThat(guestResponse).isInstanceOf(GuestResponse.class),
                () -> assertThat(guestResponse.getName()).isEqualTo(name),
                () -> assertThat(guestResponse.getEmail()).isEqualTo(email)
        );

    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    void NoGuest_loginFailTest() {
        given(guestRepository.findByEmail(email)).willReturn(Optional.empty());

        assertThrows(EmailNotFoundException.class,
                () -> guestService.login(guestRequest)
        );
    }

    @Test
    void registerTest() {
        given(guestRepository.save(guest)).willReturn(guest);

        GuestResponse guestResponse = guestService.register(guestRequest);

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
                () -> guestService.register(guestRequest)
        );
    }
}
