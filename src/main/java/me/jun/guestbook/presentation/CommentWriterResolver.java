package me.jun.guestbook.presentation;

import me.jun.guestbook.application.CommentWriterService;
import me.jun.common.security.JwtProvider;
import me.jun.guestbook.application.dto.CommentWriterInfo;
import me.jun.support.ResolverTemplate;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CommentWriterResolver extends ResolverTemplate {

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
    protected Mono<CommentWriterInfo> getUser(String email) {
        return Mono.fromCompletionStage(
                commentWriterService.retrieveCommentWriterBy(email)
        );
    }
}
