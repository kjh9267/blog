package me.jun.support;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jun.common.security.InvalidTokenException;
import me.jun.common.security.JwtProvider;
import me.jun.member.application.dto.MemberInfo;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtSubjectResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider provider;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(Member.class);
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter methodParameter,
                                        BindingContext bindingContext,
                                        ServerWebExchange serverWebExchange) {

        String token = extractToken(serverWebExchange)
                .orElseThrow(() -> new InvalidTokenException("No token"));

        provider.validateToken(token);
        String email = provider.extractSubject(token);
        return Mono.just(
                MemberInfo.from(email)
        );
    }

    private Optional<String> extractToken(ServerWebExchange serverWebExchange) {
        return Optional.ofNullable(
                serverWebExchange.getRequest()
                        .getHeaders()
                        .getFirst(AUTHORIZATION)
        );
    }
}
