package me.jun.guestbook.presentation.hateoas;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.dto.CommentResponse;
import me.jun.guestbook.application.dto.PagedCommentsResponse;
import me.jun.common.hateoas.CollectionModelCreator;
import me.jun.common.hateoas.EntityModelCreator;
import me.jun.common.hateoas.HyperlinksCreator;
import me.jun.guestbook.presentation.CommentController;
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
public class CommentEntityModelCreator implements EntityModelCreator<CommentResponse>, HyperlinksCreator, CollectionModelCreator<PagedCommentsResponse, CommentResponse> {

    private final Class<CommentController> commentController = CommentController.class;

    @Override
    public EntityModel<CommentResponse> createEntityModel(CommentResponse resource) {
        EntityModel<CommentResponse> entityModel = EntityModel.of(resource);
        WebMvcLinkBuilder controllerLink = linkTo(commentController);
        WebMvcLinkBuilder selfLinkBuilder = controllerLink
                .slash(resource.getId());

        return entityModel.add(selfLinkBuilder.withSelfRel())
                .add(controllerLink.withRel(CREATE_COMMENT))
                .add(selfLinkBuilder.withRel(GET_COMMENT))
                .add(selfLinkBuilder.withRel(UPDATE_COMMENT))
                .add(selfLinkBuilder.withRel(DELETE_COMMENT))
                .add(linkTo(commentController).slash(QUERY).slash(POST_ID).withRel(QUERY_COMMENTS_BY_POST));
    }

    @Override
    public RepresentationModel createHyperlinks() {
        return new RepresentationModel<>()
                .add(linkTo(commentController).withSelfRel())
                .add(linkTo(commentController).withRel(CREATE_COMMENT))
                .add(linkTo(commentController).slash(QUERY).slash(POST_ID).withRel(QUERY_COMMENTS_BY_POST));
    }

    @Override
    public CollectionModel<EntityModel<CommentResponse>> createCollectionModel(PagedCommentsResponse response) {
        return PagedModel.of(response.getCommentResponses().map(EntityModel::of))
                .add(linkTo(commentController).slash(QUERY).slash(POST_ID).withSelfRel())
                .add(linkTo(commentController).slash(QUERY).slash(POST_ID).withRel(QUERY_COMMENTS_BY_POST))
                .add(linkTo(commentController).withRel(CREATE_COMMENT));
    }
}
