package me.jun.member.presentation;

import me.jun.member.application.MemberService;
import me.jun.member.application.dto.MemberResponse;
import me.jun.common.security.InvalidTokenException;
import me.jun.common.security.JwtProvider;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
public class MemberResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider provider;

    private final MemberService memberService;

    public MemberResolver(JwtProvider provider, MemberService memberService) {
        this.provider = provider;
        this.memberService = memberService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(MemberInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        String token = extractToken(nativeWebRequest)
                .orElseThrow(() -> new InvalidTokenException("No token"));

        provider.validateToken(token);
        String email = provider.extractSubject(token);
        MemberResponse memberResponse = memberService.retrieveMemberBy(email);
        return me.jun.member.application.dto.MemberInfo.from(memberResponse);
    }

    private Optional<String> extractToken(NativeWebRequest nativeWebRequest) {
        return Optional.ofNullable(nativeWebRequest
                .getHeader(HttpHeaders.AUTHORIZATION));
    }
}
