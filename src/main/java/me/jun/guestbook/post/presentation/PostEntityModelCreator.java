package me.jun.guestbook.post.presentation;

import lombok.RequiredArgsConstructor;
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

    public EntityModel<PostResponse> createRepresentationModel(PostResponse resource, Class controller) {
        EntityModel<PostResponse> entityModel = EntityModel.of(resource);
        WebMvcLinkBuilder controllerLink = linkTo(controller);
        WebMvcLinkBuilder selfLinkBuilder = controllerLink
                .slash(resource.getId());

        entityModel.add(selfLinkBuilder.withSelfRel());
        entityModel.add(controllerLink.withRel(CREATE_POST));
        entityModel.add(selfLinkBuilder.withRel(GET_POST));
        entityModel.add(selfLinkBuilder.withRel(UPDATE_POST));
        entityModel.add(selfLinkBuilder.withRel(DELETE_POST));
        return entityModel;
    }

    public RepresentationModel createRepresentationModel(Class controller) {
        return new RepresentationModel<>()
                .add(linkTo(controller).withSelfRel())
                .add(linkTo(controller).withRel(CREATE_POST));
    }
}
