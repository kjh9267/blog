package me.jun.guestbook.utils;

import me.jun.guestbook.post.presentation.PostController;
import me.jun.guestbook.post.presentation.dto.PostResponse;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class SelfUriUtils {

    public static URI postSelfUri(PostResponse postResponse) {
        return linkTo(PostController.class)
                .slash(postResponse.getId())
                .withSelfRel()
                .toUri();
    }
}
