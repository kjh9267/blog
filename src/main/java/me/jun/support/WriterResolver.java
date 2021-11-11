package me.jun.support;

import lombok.extern.slf4j.Slf4j;
import me.jun.common.security.InvalidTokenException;
import me.jun.common.security.JwtProvider;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Iterator;
import java.util.Optional;

@Slf4j
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

        Iterator<String> headerNames = nativeWebRequest.getHeaderNames();
        while (headerNames.hasNext()) {
            log.info(headerNames.next());
        }

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
