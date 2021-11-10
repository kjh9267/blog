package me.jun.guestbook.presentation;

import me.jun.guestbook.application.CommentWriterService;
import me.jun.common.security.JwtProvider;
import me.jun.support.WriterResolver;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

@Component
public class CommentWriterResolver extends WriterResolver {

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
    protected Object getWriter(String email) {
        return commentWriterService.retrieveCommentWriterBy(email);
    }
}
