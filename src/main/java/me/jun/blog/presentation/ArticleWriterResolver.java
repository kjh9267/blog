package me.jun.blog.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.ArticleWriterService;
import me.jun.common.security.JwtProvider;
import me.jun.support.WriterResolver;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

@Component
public class ArticleWriterResolver extends WriterResolver {

    private final ArticleWriterService articleWriterService;

    public ArticleWriterResolver(JwtProvider jwtProvider, ArticleWriterService articleWriterService) {
        super(jwtProvider);
        this.articleWriterService = articleWriterService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ArticleWriter.class);
    }

    @Override
    protected Object getWriter(String email) {
        return articleWriterService.retrieveArticleWriter(email);
    }
}
