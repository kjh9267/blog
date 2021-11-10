package me.jun.guest.presentation;

import me.jun.guest.application.GuestService;
import me.jun.guest.application.dto.GuestInfo;
import me.jun.guest.application.dto.GuestResponse;
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
        GuestResponse guestResponse = guestService.retrieveGuestBy(email);
        return GuestInfo.from(guestResponse);
    }

    private Optional<String> extractToken(NativeWebRequest nativeWebRequest) {
        return Optional.ofNullable(nativeWebRequest
                .getHeader(HttpHeaders.AUTHORIZATION));
    }
}
