package me.jun.guestbook.post.presentation;

import me.jun.guestbook.post.presentation.dto.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PostEntityModelCreatorTest {

    private PostEntityModelCreator creator;

    private PostResponse resource;

    private Class controller;

    @BeforeEach
    void setUp() {
        creator = new PostEntityModelCreator();

        resource = PostResponse.builder()
                .id(1L)
                .title("test title")
                .content("test content")
                .build();

        controller = PostController.class;
    }

    @Test
    void createEntityModelTest() {
        assertAll(
                () -> assertThat(creator.createRepresentationModel(resource, controller))
                        .isInstanceOf(EntityModel.class),
                () -> assertThat(creator.createRepresentationModel(resource, controller).getContent())
                        .isEqualToComparingFieldByField(resource)
        );
    }

    @Test
    void creatRepresentationModelTest() {
        assertThat(creator.createRepresentationModel(controller))
                .isInstanceOf(RepresentationModel.class);
    }
}