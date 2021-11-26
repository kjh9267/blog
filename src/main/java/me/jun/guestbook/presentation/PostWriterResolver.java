package me.jun.guestbook.presentation;

import me.jun.guestbook.application.PostWriterService;
import me.jun.common.security.JwtProvider;
import me.jun.guestbook.application.dto.PostWriterInfo;
import me.jun.support.ResolverTemplate;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PostWriterResolver extends ResolverTemplate {

    private final PostWriterService postWriterService;

    public PostWriterResolver(JwtProvider provider, PostWriterService postWriterService) {
        super(provider);
        this.postWriterService = postWriterService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(PostWriter.class);
    }

    @Override
    protected Mono<PostWriterInfo> getUser(String email) {
        return Mono.fromCompletionStage(
                postWriterService.retrievePostWriterBy(email)
        );
    }
}
