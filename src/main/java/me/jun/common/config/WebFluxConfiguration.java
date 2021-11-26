package me.jun.common.config;

import lombok.RequiredArgsConstructor;
import me.jun.blog.presentation.ArticleWriterResolver;
import me.jun.guestbook.presentation.CommentWriterResolver;
import me.jun.guestbook.presentation.PostWriterResolver;
import me.jun.member.presentation.MemberResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebFluxConfiguration implements WebFluxConfigurer {

    private final PostWriterResolver postWriterResolver;

    private final CommentWriterResolver commentWriterResolver;

    private final MemberResolver memberResolver;

    private final ArticleWriterResolver articleWriterResolver;

    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(postWriterResolver);
        configurer.addCustomResolver(commentWriterResolver);
        configurer.addCustomResolver(memberResolver);
        configurer.addCustomResolver(articleWriterResolver);
    }


}
