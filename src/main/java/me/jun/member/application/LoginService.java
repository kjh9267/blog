package me.jun.member.application;

import lombok.RequiredArgsConstructor;
import me.jun.common.security.JwtProvider;
import me.jun.member.application.dto.MemberRequest;
import me.jun.member.application.dto.TokenResponse;
import me.jun.member.application.exception.MemberNotFoundException;
import me.jun.member.domain.Member;
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
    public TokenResponse login(MemberRequest request) {
        String email = request.getEmail();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));

        member.validate(request.getPassword());
        String jwt = jwtProvider.createJwt(email);

        return TokenResponse.from(jwt);
    }
}
