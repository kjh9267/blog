package me.jun.support;

import lombok.extern.slf4j.Slf4j;
import me.jun.common.security.InvalidTokenException;
import me.jun.common.security.JwtProvider;
import org.springframework.core.MethodParameter;
import org.springframework.web.reactive.BindingContext;
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public abstract class ResolverTemplate implements HandlerMethodArgumentResolver {

    private final JwtProvider provider;

    public ResolverTemplate(JwtProvider provider) {
        this.provider = provider;
    }

    @Override
    public Mono<Object> resolveArgument(MethodParameter methodParameter,
                                        BindingContext bindingContext,
                                        ServerWebExchange serverWebExchange) {
        String token = extractToken(serverWebExchange)
                .orElseThrow(() -> new InvalidTokenException("No token"));

        provider.validateToken(token);
        String email = provider.extractSubject(token);
        return getUser(email);
    }

    private Optional<String> extractToken(ServerWebExchange serverWebExchange) {
        return Optional.ofNullable(
                serverWebExchange.getRequest()
                        .getHeaders()
                        .getFirst(AUTHORIZATION)
        );
    }

    abstract protected <T> Mono<T> getUser(String email);
}
