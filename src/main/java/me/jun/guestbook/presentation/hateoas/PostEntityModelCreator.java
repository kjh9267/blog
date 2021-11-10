package me.jun.guestbook.presentation.hateoas;

import lombok.RequiredArgsConstructor;
import me.jun.common.hateoas.CollectionModelCreator;
import me.jun.guestbook.application.dto.PagedPostsResponse;
import me.jun.guestbook.application.dto.PostResponse;
import me.jun.common.hateoas.EntityModelCreator;
import me.jun.common.hateoas.HyperlinksCreator;
import me.jun.guestbook.presentation.CommentController;
import me.jun.guestbook.presentation.PostController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static me.jun.support.RelUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
@RequiredArgsConstructor
public class PostEntityModelCreator implements HyperlinksCreator, EntityModelCreator<PostResponse>, CollectionModelCreator<PagedPostsResponse, PostResponse> {

    private final Class<PostController> postController = PostController.class;

    private final Class<CommentController> commentController = CommentController.class;

    @Override
    public EntityModel<PostResponse> createEntityModel(PostResponse resource) {
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

    @Override
    public RepresentationModel createHyperlinks() {
        return new RepresentationModel<>()
                .add(linkTo(postController).withSelfRel())
                .add(linkTo(postController).slash(QUERY).withRel(QUERY_POSTS))
                .add(linkTo(postController).withRel(CREATE_POST));
    }

    @Override
    public CollectionModel<EntityModel<PostResponse>> createCollectionModel(PagedPostsResponse response) {
        return PagedModel.of(response.getPostResponses().map(EntityModel::of))
                .add(linkTo(postController).slash(QUERY).withSelfRel())
                .add(linkTo(postController).withRel(CREATE_POST))
                .add(linkTo(postController).withRel(GET_POST));
    }
}
