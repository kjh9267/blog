package me.jun.guestbook.post.presentation;

import me.jun.guestbook.comment.application.CommentWriterService;
import me.jun.guestbook.comment.domain.Comment;
import me.jun.guestbook.comment.presentation.CommentWriter;
import me.jun.guestbook.post.application.PostWriterService;
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
public class CommentWriterResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider provider;

    private final CommentWriterService commentWriterService;

    public CommentWriterResolver(JwtProvider provider, CommentWriterService commentWriterService) {
        this.provider = provider;
        this.commentWriterService = commentWriterService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CommentWriter.class);
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
        return commentWriterService.retrieveCommentWriterBy(email);
    }

    private Optional<String> extractToken(NativeWebRequest nativeWebRequest) {
        return Optional.ofNullable(nativeWebRequest
                .getHeader(HttpHeaders.AUTHORIZATION));
    }
}
