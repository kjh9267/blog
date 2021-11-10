package me.jun.common.hateoas;

import org.springframework.hateoas.EntityModel;

public interface EntityModelCreator<T> {

    EntityModel<T> createEntityModel(T resource);
}
