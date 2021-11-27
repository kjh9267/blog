package me.jun.blog.presentation;

import me.jun.blog.application.ArticleWriterService;
import me.jun.blog.application.dto.ArticleWriterInfo;
import me.jun.common.security.JwtProvider;
import me.jun.support.ResolverTemplate;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class ArticleWriterResolver extends ResolverTemplate<ArticleWriterInfo> {

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
    protected CompletableFuture<ArticleWriterInfo> getUser(String email) {
        return articleWriterService.retrieveArticleWriter(email);
    }
}
