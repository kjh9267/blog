package me.jun.guestbook.comment.presentation;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import me.jun.guestbook.common.EntityModelCreator;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static me.jun.guestbook.utils.RelUtils.*;
import static me.jun.guestbook.utils.RelUtils.DELETE_COMMENT;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Component
@RequiredArgsConstructor
public class CommentEntityModelCreator implements EntityModelCreator<CommentResponse> {

    @Override
    public EntityModel<CommentResponse> createEntityModel(CommentResponse resource, Class controller) {
        EntityModel<CommentResponse> entityModel = EntityModel.of(resource);
        WebMvcLinkBuilder controllerLink = linkTo(controller);
        WebMvcLinkBuilder selfLinkBuilder = controllerLink
                .slash(resource.getId());

        entityModel.add(selfLinkBuilder.withSelfRel());
        entityModel.add(controllerLink.withRel(CREATE_COMMENT));
        entityModel.add(selfLinkBuilder.withRel(GET_COMMENT));
        entityModel.add(selfLinkBuilder.withRel(UPDATE_COMMENT));
        entityModel.add(selfLinkBuilder.withRel(DELETE_COMMENT));
        return entityModel;

    }

    @Override
    public RepresentationModel createEntityModel(Class controller) {
        return new RepresentationModel<>()
                .add(linkTo(controller).withSelfRel())
                .add(linkTo(controller).withRel(CREATE_COMMENT));
    }
}
