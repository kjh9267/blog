package me.jun.guestbook.post.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.presentation.CommentController;
import me.jun.guestbook.post.presentation.dto.PagedPostsResponse;
import me.jun.guestbook.post.presentation.dto.PostResponse;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static me.jun.guestbook.utils.RelUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
public class PostEntityModelCreator {

    private final Class<PostController> postController = PostController.class;

    private final Class<CommentController> commentController = CommentController.class;

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
                .add(linkTo(postController).slash(QUERY).withRel(QUERY_POSTS))
                .add(linkTo(commentController).slash(QUERY).slash(POST_ID).withRel(QUERY_COMMENTS_BY_POST));
    }

    public RepresentationModel createRepresentationModel() {
        return new RepresentationModel<>()
                .add(linkTo(postController).withSelfRel())
                .add(linkTo(postController).slash(QUERY).withRel(QUERY_POSTS))
                .add(linkTo(postController).withRel(CREATE_POST));
    }

    public CollectionModel<EntityModel<PostResponse>> createCollectionModel(PagedPostsResponse response) {
        return PagedModel.of(response.getPostResponses().map(EntityModel::of))
                .add(linkTo(postController).slash(QUERY).withSelfRel())
                .add(linkTo(postController).withRel(CREATE_POST))
                .add(linkTo(postController).withRel(GET_POST));
    }
}
