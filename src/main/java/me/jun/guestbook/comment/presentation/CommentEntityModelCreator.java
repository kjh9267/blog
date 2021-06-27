package me.jun.guestbook.comment.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import me.jun.guestbook.comment.presentation.dto.PagedCommentsResponse;
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
public class CommentEntityModelCreator {

    private final Class<CommentController> commentController = CommentController.class;

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

    public RepresentationModel createRepresentationModel() {
        return new RepresentationModel<>()
                .add(linkTo(commentController).withSelfRel())
                .add(linkTo(commentController).withRel(CREATE_COMMENT))
                .add(linkTo(commentController).slash(QUERY).slash(POST_ID).withRel(QUERY_COMMENTS_BY_POST));
    }

    public CollectionModel<EntityModel<CommentResponse>> createCollectionModel(PagedCommentsResponse response) {
        return PagedModel.of(response.getCommentResponses().map(EntityModel::of))
                .add(linkTo(commentController).slash(QUERY).slash(POST_ID).withSelfRel())
                .add(linkTo(commentController).slash(QUERY).slash(POST_ID).withRel(QUERY_COMMENTS_BY_POST))
                .add(linkTo(commentController).withRel(CREATE_COMMENT));
    }
}
