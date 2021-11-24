package me.jun.common.config;

import lombok.RequiredArgsConstructor;
import me.jun.blog.presentation.ArticleWriterResolver;
import me.jun.common.interceptor.BlogInterceptor;
import me.jun.guestbook.presentation.CommentWriterResolver;
import me.jun.guestbook.presentation.PostWriterResolver;
import me.jun.member.presentation.MemberResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    private final PostWriterResolver postWriterResolver;

    private final CommentWriterResolver commentWriterResolver;

    private final MemberResolver memberResolver;

    private final ArticleWriterResolver articleWriterResolver;

    private final BlogInterceptor blogInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(postWriterResolver);
        resolvers.add(commentWriterResolver);
        resolvers.add(memberResolver);
        resolvers.add(articleWriterResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(blogInterceptor)
                .addPathPatterns("/api/articles/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
