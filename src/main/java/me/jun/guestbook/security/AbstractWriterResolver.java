package me.jun.guestbook.security;

import me.jun.guestbook.post.presentation.PostWriter;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;


public abstract class AbstractWriterResolver<T> implements HandlerMethodArgumentResolver {
    private final JwtProvider provider;

    protected AbstractWriterResolver(JwtProvider provider) {
        this.provider = provider;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(PostWriter.class);
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
        return retrieveWriterBy(email);
    }

    protected abstract T retrieveWriterBy(String email);

    private Optional<String> extractToken(NativeWebRequest nativeWebRequest) {
        return Optional.ofNullable(nativeWebRequest
                .getHeader(HttpHeaders.AUTHORIZATION));
    }
}
