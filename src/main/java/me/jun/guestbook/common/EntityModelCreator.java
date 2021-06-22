package me.jun.guestbook.common;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

public interface EntityModelCreator<T> {

    EntityModel<T> createEntityModel(T Resource, Class controller);

    RepresentationModel createEntityModel(Class controller);
}
