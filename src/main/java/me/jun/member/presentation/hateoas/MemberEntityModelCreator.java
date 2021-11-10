package me.jun.member.presentation.hateoas;

import me.jun.member.application.dto.TokenResponse;
import me.jun.common.hateoas.EntityModelCreator;
import me.jun.common.hateoas.HyperlinksCreator;
import me.jun.member.presentation.MemberController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import static me.jun.support.RelUtils.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class MemberEntityModelCreator implements HyperlinksCreator, EntityModelCreator<TokenResponse> {

    private final Class<MemberController> controller = MemberController.class;

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
