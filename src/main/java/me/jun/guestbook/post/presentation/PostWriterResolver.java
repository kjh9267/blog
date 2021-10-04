package me.jun.guestbook.post.presentation;

import me.jun.guestbook.post.application.PostWriterService;
import me.jun.guestbook.security.JwtProvider;
import me.jun.guestbook.support.WriterResolver;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

@Component
public class PostWriterResolver extends WriterResolver {

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
    protected Object getWriter(String email) {
        return postWriterService.retrievePostWriterBy(email);
    }
}
