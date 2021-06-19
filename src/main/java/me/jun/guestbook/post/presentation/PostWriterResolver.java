package me.jun.guestbook.post.presentation;

import me.jun.guestbook.post.application.PostWriterService;
import me.jun.guestbook.post.presentation.dto.PostWriterInfo;
import me.jun.guestbook.security.AbstractWriterResolver;
import me.jun.guestbook.security.JwtProvider;
import org.springframework.stereotype.Component;

@Component
public class PostWriterResolver extends AbstractWriterResolver<PostWriterInfo> {

    private final PostWriterService postWriterService;

    public PostWriterResolver(PostWriterService postWriterService, JwtProvider provider) {
        super(provider);
        this.postWriterService = postWriterService;
    }

    @Override
    protected PostWriterInfo retrieveWriterBy(String email) {
        return postWriterService.retrievePostWriterBy(email);
    }
}
