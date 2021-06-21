package me.jun.guestbook.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.guestbook.comment.presentation.CommentController;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import me.jun.guestbook.post.presentation.PostController;
import me.jun.guestbook.post.presentation.dto.PostResponse;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class SelfUriUtils {

    public static URI postSelfUri(PostResponse postResponse) {
        return linkTo(PostController.class)
                .slash(postResponse.getId())
                .withSelfRel()
                .toUri();
    }

    public static URI commentSelfUri(CommentResponse commentResponse) {
        return linkTo(CommentController.class)
                .slash(commentResponse.getId())
                .withSelfRel()
                .toUri();
    }
}
