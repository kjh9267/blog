package me.jun.guestbook.guest.presentation;

import me.jun.guestbook.guest.application.GuestService;
import me.jun.guestbook.post.application.PostWriterService;
import me.jun.guestbook.post.presentation.PostWriter;
import me.jun.guestbook.security.InvalidTokenException;
import me.jun.guestbook.security.JwtProvider;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

@Component
public class GuestResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider provider;

    private final GuestService guestService;

    public GuestResolver(JwtProvider provider, GuestService guestService) {
        this.provider = provider;
        this.guestService = guestService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(Guest.class);
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
        return guestService.retrieveGuestBy(email);
    }

    private Optional<String> extractToken(NativeWebRequest nativeWebRequest) {
        return Optional.ofNullable(nativeWebRequest
                .getHeader(HttpHeaders.AUTHORIZATION));
    }
}
