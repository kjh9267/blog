package me.jun.guestbook.guest.application;

import me.jun.guestbook.guest.application.exception.DuplicatedEmailException;
import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
import me.jun.guestbook.guest.presentation.dto.GuestRequest;
import me.jun.guestbook.guest.presentation.dto.GuestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    private RegisterService registerService;

    @Mock
    private GuestRepository guestRepository;

    private Guest guest;

    private GuestRequest guestRequest;

    private GuestResponse guestResponse;

    @BeforeEach
    void setUp() {
        registerService = new RegisterService(guestRepository);

        guest = Guest.builder()
                .id(1L)
                .name("test")
                .email("testuser@email.com")
                .build();

        guestRequest = GuestRequest.builder()
                .name("test")
                .email("testuser@email.com")
                .password("pass")
                .build();

        guestResponse = GuestResponse.builder()
                .id(1L)
                .name("test")
                .email("testuser@email.com")
                .build();
    }

    @Test
    void registerTest() {
        given(guestRepository.save(any()))
                .willReturn(guest);

        assertThat(registerService.register(guestRequest))
                .isEqualToComparingFieldByField(guestResponse);
    }

    @Test
    void duplicatedEmail_registerFailTest() {
        given(guestRepository.save(any()))
                .willThrow(DuplicatedEmailException.class);

        assertThrows(DuplicatedEmailException.class,
                () -> assertThat(registerService.register(guestRequest))
        );
    }
}