package me.jun.member.application;

import lombok.RequiredArgsConstructor;
import me.jun.common.security.JwtProvider;
import me.jun.member.application.dto.LoginRequest;
import me.jun.member.application.dto.TokenResponse;
import me.jun.member.application.exception.MemberNotFoundException;
import me.jun.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LoginService {

    private final MemberRepository memberRepository;

    private final JwtProvider jwtProvider;

    @Transactional(readOnly = true)
    public TokenResponse login(LoginRequest request) {
        String email = request.getEmail();
        memberRepository.findByEmail(email)
                .map(member -> member.validate(request.getPassword()))
                .orElseThrow(() -> new MemberNotFoundException(email));

        String jwt = jwtProvider.createJwt(email);

        return TokenResponse.from(jwt);
    }
}
