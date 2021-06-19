package me.jun.guestbook.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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
}
