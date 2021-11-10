package me.jun.guestbook.presentation;

import me.jun.guestbook.application.PostWriterService;
import me.jun.common.security.JwtProvider;
import me.jun.support.WriterResolver;
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
