package me.jun.member.presentation;

import me.jun.member.application.MemberService;
import me.jun.member.application.dto.MemberResponse;
import me.jun.common.security.InvalidTokenException;
import me.jun.common.security.JwtProvider;
import me.jun.support.ResolverTemplate;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Component
public class MemberResolver extends ResolverTemplate {

    private final MemberService memberService;

    public MemberResolver(JwtProvider provider, MemberService memberService) {
        super(provider);
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(MemberInfo.class);
    }

    @Override
    protected Mono<MemberResponse> getUser(String email) {
        return Mono.fromCompletionStage(
                memberService.retrieveMemberBy(email)
        );
    }
}
