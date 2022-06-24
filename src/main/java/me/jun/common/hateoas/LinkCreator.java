package me.jun.common.hateoas;

import org.springframework.hateoas.RepresentationModel;

public interface LinkCreator {

    <T extends RepresentationModel<? extends T>> void createLink(Class<?> controller, RepresentationModel<T> response);
}
