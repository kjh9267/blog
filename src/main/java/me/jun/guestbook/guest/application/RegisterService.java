package me.jun.guestbook.guest.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.guest.application.exception.DuplicatedEmailException;
import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
import me.jun.guestbook.guest.presentation.dto.GuestRequest;
import me.jun.guestbook.guest.presentation.dto.GuestResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterService {

    private final GuestRepository guestRepository;

    public GuestResponse register(GuestRequest request) {
        Guest guest = request.toEntity();

        try {
            guest = guestRepository.save(guest);
            return GuestResponse.from(guest);
        }
        catch (DataIntegrityViolationException e) {
            throw new DuplicatedEmailException(e);
        }
    }
}
