package me.jun.guestbook.home.presentation;

import me.jun.guestbook.comment.presentation.CommentController;
import me.jun.guestbook.guest.presentation.GuestController;
import me.jun.guestbook.post.presentation.PostController;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import static me.jun.guestbook.utils.RelUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class HomeRepresentationModelCreator {

    private final Class<PostController> postController = PostController.class;

    private final Class<CommentController> commentController = CommentController.class;

    private final Class<GuestController> guestController = GuestController.class;

    public RepresentationModel createRepresentationModel() {
        return new RepresentationModel()
                .add(linkTo(postController).slash(QUERY).withRel(QUERY_POSTS))
                .add(linkTo(commentController).slash(QUERY).slash(POST_ID).withRel(QUERY_COMMENTS_BY_POST))
                .add(linkTo(guestController).slash(REGISTER).withRel(REGISTER))
                .add(linkTo(guestController).slash(LOGIN).withRel(LOGIN));
    }
}
