package me.jun.guestbook.support;

import me.jun.guestbook.security.InvalidTokenException;
import me.jun.guestbook.security.JwtProvider;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

public abstract class WriterResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider provider;

    public WriterResolver(JwtProvider provider) {
        this.provider = provider;
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
        return getWriter(email);
    }

    private Optional<String> extractToken(NativeWebRequest nativeWebRequest) {
        return Optional.ofNullable(nativeWebRequest
                .getHeader(HttpHeaders.AUTHORIZATION));
    }

    abstract protected Object getWriter(String email);
}
