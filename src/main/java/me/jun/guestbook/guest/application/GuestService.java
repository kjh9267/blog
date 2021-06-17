package me.jun.guestbook.guest.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.guest.application.exception.GuestNotFoundException;
import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
import me.jun.guestbook.guest.presentation.dto.GuestResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestResponse retrieveGuestBy(String email) {
        Guest guest = guestRepository.findByEmail(email)
                .orElseThrow(() -> new GuestNotFoundException());

        return GuestResponse.from(guest);
    }
}
