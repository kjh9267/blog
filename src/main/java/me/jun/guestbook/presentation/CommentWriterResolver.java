package me.jun.guestbook.presentation;

import me.jun.common.security.JwtProvider;
import me.jun.guestbook.application.CommentWriterService;
import me.jun.guestbook.application.dto.CommentWriterInfo;
import me.jun.support.ResolverTemplate;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class CommentWriterResolver extends ResolverTemplate<CommentWriterInfo> {

    private final CommentWriterService commentWriterService;

    public CommentWriterResolver(JwtProvider jwtProvider, CommentWriterService commentWriterService) {
        super(jwtProvider);
        this.commentWriterService = commentWriterService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CommentWriter.class);
    }

    @Override
    protected CompletableFuture<CommentWriterInfo> getUser(String email) {
        return commentWriterService.retrieveCommentWriterBy(email);
    }
}
