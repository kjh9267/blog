package me.jun.guestbook.guest.presentation;

import me.jun.guestbook.guest.application.dto.TokenResponse;
import me.jun.guestbook.hateoas.EntityModelCreator;
import me.jun.guestbook.hateoas.HyperlinksCreator;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import static me.jun.guestbook.support.RelUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class GuestEntityModelCreator implements HyperlinksCreator, EntityModelCreator<TokenResponse> {

    private final Class<GuestController> controller = GuestController.class;

    @Override
    public EntityModel<TokenResponse> createEntityModel(TokenResponse tokenResponse) {
        return EntityModel.of(tokenResponse)
                .add(linkTo(controller).slash(LOGIN).withSelfRel())
                .add(linkTo(controller).slash(REGISTER).withRel(REGISTER));
    }

    @Override
    public RepresentationModel createHyperlinks() {
        return new RepresentationModel()
                .add(linkTo(controller).slash(LEAVE).withRel(LEAVE))
                .add(linkTo(controller).slash(LOGIN).withRel(LOGIN))
                .add(linkTo(controller).slash(REGISTER).withRel(REGISTER));
    }
}
