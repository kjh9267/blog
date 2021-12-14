package me.jun.common;

import lombok.RequiredArgsConstructor;
import me.jun.common.security.JwtProvider;
import me.jun.member.application.MemberService;
import me.jun.member.application.dto.MemberInfo;
import me.jun.member.domain.Role;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberExtractor {

    private final MemberService memberService;

    private final JwtProvider jwtProvider;

    public MemberInfo extractMemberFrom(String token) {
        jwtProvider.validateToken(token);
        String email = jwtProvider.extractSubject(token);

        Role role = memberService.retrieveMemberBy(email)
                .getRole();

        return MemberInfo.from(email, role);
    }
}
