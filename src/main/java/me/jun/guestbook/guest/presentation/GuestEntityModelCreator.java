package me.jun.guestbook.guest.presentation;

import me.jun.guestbook.guest.presentation.dto.TokenResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import static me.jun.guestbook.utils.RelUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class GuestEntityModelCreator {
    private final Class<GuestController> controller = GuestController.class;

    EntityModel<TokenResponse> createLoginEntityModel(TokenResponse tokenResponse) {
        return EntityModel.of(tokenResponse)
                .add(linkTo(controller).slash(LOGIN).withSelfRel())
                .add(linkTo(controller).slash(REGISTER).withRel(REGISTER));
    }

    RepresentationModel createRegisterRepresentationModel() {
        return new RepresentationModel()
                .add(linkTo(controller).slash(REGISTER).withSelfRel())
                .add(linkTo(controller).slash(LOGIN).withRel(LOGIN));
    }

    RepresentationModel createLeaveRepresentationModel() {
        return new RepresentationModel()
                .add(linkTo(controller).slash(LEAVE).withSelfRel())
                .add(linkTo(controller).slash(REGISTER).withRel(REGISTER));
    }
}
