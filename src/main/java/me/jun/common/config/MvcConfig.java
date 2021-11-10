package me.jun.common.config;

import lombok.RequiredArgsConstructor;
import me.jun.member.presentation.MemberResolver;
import me.jun.support.WriterResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    private final WriterResolver postWriterResolver;

    private final WriterResolver commentWriterResolver;

    private final MemberResolver memberResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(postWriterResolver);
        resolvers.add(commentWriterResolver);
        resolvers.add(memberResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
}
