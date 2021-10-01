package me.jun.guestbook.hateoas;

import org.springframework.hateoas.EntityModel;

public interface EntityModelCreator<T> {

    EntityModel<T> createEntityModel(T resource);
}
