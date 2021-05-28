package me.jun.guestbook.guest.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.dto.GuestRequest;
import me.jun.guestbook.dto.GuestResponse;
import me.jun.guestbook.guest.application.exception.DuplicatedEmailException;
import me.jun.guestbook.guest.application.exception.EmailNotFoundException;
import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestResponse login(GuestRequest dto) {
        Guest guest = dto.toEntity();
        guest = guestRepository.findByEmail(guest.getEmail())
                .orElseThrow(EmailNotFoundException::new);

        guest.validate(dto.getPassword());

        return GuestResponse.from(guest);
    }

    public GuestResponse register(GuestRequest dto) {
        Guest guest = dto.toEntity();

        try {
            guest = guestRepository.save(guest);

            return GuestResponse.from(guest);
        }
        catch (DataIntegrityViolationException e) {
            throw new DuplicatedEmailException(e);
        }
    }

    public void deleteGuest(GuestRequest dto) {
        Guest guest = guestRepository.findByEmail(dto.getEmail())
                .orElseThrow(EmailNotFoundException::new);

        guest.validate(dto.getPassword());

        guestRepository.deleteById(guest.getId());
    }
}
