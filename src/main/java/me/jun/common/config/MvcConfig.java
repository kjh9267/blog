package me.jun.common.config;

import lombok.RequiredArgsConstructor;
import me.jun.common.interceptor.BlogInterceptor;
import me.jun.support.JwtSubjectResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    private final JwtSubjectResolver jwtSubjectResolver;

    private final BlogInterceptor blogInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jwtSubjectResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(blogInterceptor)
                .addPathPatterns("/api/blog/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods(
                        GET.name(),
                        POST.name(),
                        PUT.name(),
                        DELETE.name()
                )
                .allowCredentials(true);
    }
}