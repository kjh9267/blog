package me.jun.common;

import lombok.RequiredArgsConstructor;
import me.jun.common.security.JwtProvider;
import me.jun.core.member.application.MemberService;
import me.jun.core.member.application.dto.MemberInfo;
import me.jun.core.member.application.dto.MemberResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberExtractor {

    private final MemberService memberService;

    private final JwtProvider jwtProvider;

    public MemberInfo extractMemberFrom(String token) {
        jwtProvider.validateToken(token);
        String email = jwtProvider.extractSubject(token);
        MemberResponse memberResponse = memberService.retrieveMemberBy(email);

        return MemberInfo.from(memberResponse);
    }
}
