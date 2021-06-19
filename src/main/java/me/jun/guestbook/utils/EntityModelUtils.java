package me.jun.guestbook.utils;

import me.jun.guestbook.post.presentation.PostController;
import me.jun.guestbook.post.presentation.dto.PostResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static me.jun.guestbook.utils.RelUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


public class EntityModelUtils {

    public static EntityModel<PostResponse> postEntityModel(PostResponse postResponse) {
        EntityModel<PostResponse> entityModel = EntityModel.of(postResponse);
        WebMvcLinkBuilder controllerLink = linkTo(PostController.class);
        WebMvcLinkBuilder selfLinkBuilder = controllerLink
                .slash(postResponse.getId());

        entityModel.add(selfLinkBuilder.withSelfRel());
        entityModel.add(controllerLink.withRel(CREATE_POST));
        entityModel.add(selfLinkBuilder.withRel(GET_POST));
        entityModel.add(selfLinkBuilder.withRel(UPDATE_POST));
        entityModel.add(selfLinkBuilder.withRel(DELETE_POST));
        return entityModel;
    }

    public static RepresentationModel postEntityModel() {
        return new RepresentationModel<>()
                        .add(linkTo(PostController.class).withSelfRel())
                        .add(linkTo(PostController.class).withRel(CREATE_POST));
    }
}
