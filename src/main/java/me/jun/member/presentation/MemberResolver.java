package me.jun.member.presentation;

import me.jun.common.security.JwtProvider;
import me.jun.member.application.MemberService;
import me.jun.member.application.dto.MemberInfo;
import me.jun.support.ResolverTemplate;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class MemberResolver extends ResolverTemplate<MemberInfo> {

    private final MemberService memberService;

    public MemberResolver(JwtProvider provider, MemberService memberService) {
        super(provider);
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(Member.class);
    }

    @Override
    protected CompletableFuture<MemberInfo> getUser(String email) {
        return memberService.retrieveMemberBy(email)
                .thenApply(MemberInfo::from);
    }
}
