package me.jun.guestbook.common;

import me.jun.guestbook.common.error.ErrorResponse;
import me.jun.guestbook.home.presentation.HomeController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import static me.jun.guestbook.utils.RelUtils.HOME;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ErrorEntityModelCreator {

    private final Class<HomeController> homeController = HomeController.class;

    public EntityModel<ErrorResponse> createErrorEntityModel(ErrorResponse errorResponse) {
        return EntityModel.of(errorResponse)
                .add(linkTo(homeController).withRel(HOME));
    }
}
