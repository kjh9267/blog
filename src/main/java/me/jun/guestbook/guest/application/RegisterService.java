package me.jun.guestbook.guest.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.guest.application.exception.DuplicatedEmailException;
import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
import me.jun.guestbook.guest.application.dto.GuestRequest;
import me.jun.guestbook.guest.application.dto.GuestResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
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
