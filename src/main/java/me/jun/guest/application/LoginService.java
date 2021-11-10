package me.jun.guest.application;

import lombok.RequiredArgsConstructor;
import me.jun.guest.application.dto.GuestRequest;
import me.jun.guest.application.dto.TokenResponse;
import me.jun.guest.application.exception.EmailNotFoundException;
import me.jun.guest.domain.Guest;
import me.jun.guest.domain.repository.GuestRepository;
import me.jun.common.security.JwtProvider;
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
