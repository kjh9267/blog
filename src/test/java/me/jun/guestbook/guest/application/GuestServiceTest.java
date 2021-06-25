package me.jun.guestbook.guest.application;

import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
import me.jun.guestbook.guest.presentation.dto.GuestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

    private GuestService guestService;

    @Mock
    private GuestRepository guestRepository;

    private Guest guest;

    private GuestResponse guestResponse;

    @BeforeEach
    void setUp() {
        guestService = new GuestService(guestRepository);

        guest = Guest.builder()
                .id(1L)
                .name("test user")
                .email("testuser@email.com")
                .build();

        guestResponse = GuestResponse.from(guest);
    }

    @Test
    void retrieveGuestByEmailTest() {
        given(guestRepository.findByEmail(any()))
                .willReturn(Optional.of(guest));

        assertThat(guestService.retrieveGuestBy("testuser@email.com"))
                .isEqualToComparingFieldByField(guestResponse);
    }
}