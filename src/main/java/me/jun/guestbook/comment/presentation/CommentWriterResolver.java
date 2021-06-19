package me.jun.guestbook.comment.presentation;

import me.jun.guestbook.comment.application.CommentWriterService;
import me.jun.guestbook.comment.presentation.dto.CommentWriterInfo;
import me.jun.guestbook.security.AbstractWriterResolver;
import me.jun.guestbook.security.JwtProvider;
import org.springframework.stereotype.Component;

@Component
public class CommentWriterResolver extends AbstractWriterResolver<CommentWriterInfo> {

    private final CommentWriterService commentWriterService;

    public CommentWriterResolver(CommentWriterService commentWriterService, JwtProvider provider) {
        super(provider);
        this.commentWriterService = commentWriterService;
    }

    @Override
    protected CommentWriterInfo retrieveWriterBy(String email) {
        return commentWriterService.retrieveCommentWriterBy(email);
    }
}
