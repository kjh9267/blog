package me.jun.guestbook.guest.application;

import me.jun.guestbook.guest.application.exception.DuplicatedEmailException;
import me.jun.guestbook.guest.domain.GuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.jun.guestbook.guest.GuestFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    private RegisterService registerService;

    @Mock
    private GuestRepository guestRepository;

    @BeforeEach
    void setUp() {
        registerService = new RegisterService(guestRepository);
    }

    @Test
    void registerTest() {
        given(guestRepository.save(any()))
                .willReturn(guest());

        assertThat(registerService.register(guestRequest()))
                .isEqualToComparingFieldByField(guestResponse());
    }

    @Test
    void duplicatedEmail_registerFailTest() {
        given(guestRepository.save(any()))
                .willThrow(DuplicatedEmailException.class);

        assertThrows(DuplicatedEmailException.class,
                () -> assertThat(registerService.register(guestRequest()))
        );
    }
}