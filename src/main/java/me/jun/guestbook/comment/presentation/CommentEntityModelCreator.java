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

    private final String QUERY = "query";
    private final String POST_ID = "post-id";

    public EntityModel<CommentResponse> createEntityModel(CommentResponse resource, Class controller) {
        EntityModel<CommentResponse> entityModel = EntityModel.of(resource);
        WebMvcLinkBuilder controllerLink = linkTo(controller);
        WebMvcLinkBuilder selfLinkBuilder = controllerLink
                .slash(resource.getId());

        return entityModel.add(selfLinkBuilder.withSelfRel())
                .add(controllerLink.withRel(CREATE_COMMENT))
                .add(selfLinkBuilder.withRel(GET_COMMENT))
                .add(selfLinkBuilder.withRel(UPDATE_COMMENT))
                .add(selfLinkBuilder.withRel(DELETE_COMMENT))
                .add(linkTo(controller).slash(QUERY).slash(POST_ID).withRel(QUERY_COMMENTS_BY_POST));
    }

    public RepresentationModel createRepresentationModel(Class controller) {
        return new RepresentationModel<>()
                .add(linkTo(controller).withSelfRel())
                .add(linkTo(controller).withRel(CREATE_COMMENT))
                .add(linkTo(controller).slash(QUERY).slash(POST_ID).withRel(QUERY_COMMENTS_BY_POST));
    }

    public CollectionModel<EntityModel<CommentResponse>> createCollectionModel(PagedCommentsResponse response, Class controller) {
        return PagedModel.of(response.getCommentResponses().map(EntityModel::of))
                .add(linkTo(controller).slash(QUERY).slash(POST_ID).withSelfRel())
                .add(linkTo(controller).slash(QUERY).slash(POST_ID).withRel(QUERY_COMMENTS_BY_POST))
                .add(linkTo(controller).withRel(CREATE_COMMENT));
    }
}
