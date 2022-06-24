package me.jun.common.hateoas;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class DefaultLinkCreator implements LinkCreator {

    @Override
    public <T extends RepresentationModel<? extends T>> void createLink(Class<?> controller, RepresentationModel<T> response) {
        Link selfLink = linkTo(controller)
                .withSelfRel();

        response.add(selfLink);
    }
}
