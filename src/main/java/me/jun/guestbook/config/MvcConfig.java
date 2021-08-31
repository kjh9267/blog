package me.jun.guestbook.config;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.guest.presentation.GuestResolver;
import me.jun.guestbook.post.presentation.CommentWriterResolver;
import me.jun.guestbook.post.presentation.PostWriterResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    private final PostWriterResolver postWriterResolver;

    private final CommentWriterResolver commentWriterResolver;

    private final GuestResolver guestResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(postWriterResolver);
        resolvers.add(commentWriterResolver);
        resolvers.add(guestResolver);
    }
}
