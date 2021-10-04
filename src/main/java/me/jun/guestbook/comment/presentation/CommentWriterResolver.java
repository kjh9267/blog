package me.jun.guestbook.comment.presentation;

import me.jun.guestbook.comment.application.CommentWriterService;
import me.jun.guestbook.security.JwtProvider;
import me.jun.guestbook.support.WriterResolver;
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
