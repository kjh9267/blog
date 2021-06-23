package me.jun.guestbook.post.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.presentation.CommentController;
import me.jun.guestbook.post.presentation.dto.PostResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static me.jun.guestbook.utils.RelUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
public class PostEntityModelCreator {

    private final String QUERY = "query";
    private final String POST_ID = "post-id";
    private final Class<PostController> postController = PostController.class;

    public EntityModel<PostResponse> createRepresentationModel(PostResponse resource) {
        EntityModel<PostResponse> entityModel = EntityModel.of(resource);
        WebMvcLinkBuilder controllerLink = linkTo(postController);
        WebMvcLinkBuilder selfLinkBuilder = controllerLink
                .slash(resource.getId());

        return entityModel.add(selfLinkBuilder.withSelfRel())
                .add(controllerLink.withRel(CREATE_POST))
                .add(selfLinkBuilder.withRel(GET_POST))
                .add(selfLinkBuilder.withRel(UPDATE_POST))
                .add(selfLinkBuilder.withRel(DELETE_POST))
                .add(linkTo(CommentController.class).slash(QUERY).slash(POST_ID).withRel(QUERY_COMMENTS_BY_POST));
    }

    public RepresentationModel createRepresentationModel() {
        return new RepresentationModel<>()
                .add(linkTo(postController).withSelfRel())
                .add(linkTo(postController).withRel(CREATE_POST));
    }
}
