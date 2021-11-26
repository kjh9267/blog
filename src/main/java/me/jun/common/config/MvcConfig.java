//package me.jun.common.config;
//
//import lombok.RequiredArgsConstructor;
//import me.jun.blog.presentation.ArticleResolverTemplate;
//import me.jun.common.interceptor.BlogInterceptor;
//import me.jun.guestbook.presentation.CommentResolverTemplate;
//import me.jun.guestbook.presentation.PostResolverTemplate;
//import me.jun.member.presentation.MemberResolver;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import java.util.List;
//
//@Configuration
//@RequiredArgsConstructor
//public class MvcConfig implements WebMvcConfigurer {
//
//    private final PostResolverTemplate postWriterResolver;
//
//    private final CommentResolverTemplate commentWriterResolver;
//
//    private final MemberResolver memberResolver;
//
//    private final ArticleResolverTemplate articleWriterResolver;
//
//    private final BlogInterceptor blogInterceptor;
//
//    @Override
//    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(postWriterResolver);
//        resolvers.add(commentWriterResolver);
//        resolvers.add(memberResolver);
//        resolvers.add(articleWriterResolver);
//    }
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("*");
//    }
//}
