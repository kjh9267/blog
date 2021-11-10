package me.jun.guestbook.presentation.hateoas;

import me.jun.guest.presentation.GuestController;
import me.jun.common.hateoas.HyperlinksCreator;
import me.jun.guestbook.presentation.CommentController;
import me.jun.guestbook.presentation.PostController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import static me.jun.support.RelUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class HomeRepresentationModelCreator implements HyperlinksCreator {

    private final Class<PostController> postController = PostController.class;

    private final Class<CommentController> commentController = CommentController.class;

    private final Class<GuestController> guestController = GuestController.class;

    @Override
    public RepresentationModel createHyperlinks() {
        return new RepresentationModel()
                .add(linkTo(postController).slash(QUERY).withRel(QUERY_POSTS))
                .add(linkTo(commentController).slash(QUERY).slash(POST_ID).withRel(QUERY_COMMENTS_BY_POST))
                .add(linkTo(guestController).slash(REGISTER).withRel(REGISTER))
                .add(linkTo(guestController).slash(LOGIN).withRel(LOGIN));
    }
}
