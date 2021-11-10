package me.jun.common.hateoas;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface CollectionModelCreator<T, R> {

    CollectionModel<EntityModel<R>> createCollectionModel(T resource);
}
