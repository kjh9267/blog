package me.jun.guestbook.guest.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.guest.presentation.dto.GuestRequest;
import me.jun.guestbook.guest.presentation.dto.GuestResponse;
import me.jun.guestbook.guest.presentation.dto.TokenResponse;
import me.jun.guestbook.guest.application.exception.DuplicatedEmailException;
import me.jun.guestbook.guest.application.exception.EmailNotFoundException;
import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
import me.jun.guestbook.security.JwtProvider;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final GuestRepository guestRepository;

    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    public TokenResponse login(GuestRequest request) {
        String email = request.getEmail();
        Guest guest = guestRepository.findByEmail(email)
                .orElseThrow(EmailNotFoundException::new);

        guest.validate(request.getPassword());
        String jwt = jwtProvider.createJwt(email);

        return TokenResponse.from(jwt);
    }
}
