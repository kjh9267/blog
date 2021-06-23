package me.jun.guestbook.comment.presentation;

import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
class CommentEntityModelCreatorTest {

    private CommentEntityModelCreator creator;

    private CommentResponse resource;

    private Class controller;

    @BeforeEach
    void setUp() {
        creator = new CommentEntityModelCreator();

        resource = CommentResponse.builder()
                .id(1L)
                .postId(1L)
                .writerId(1L)
                .content("test content")
                .build();

        controller = CommentController.class;
    }

    @Test
    void createEntityModelTest() {
        assertAll(
                () -> assertThat(creator.createEntityModel(resource, controller))
                        .isInstanceOf(EntityModel.class),
                () -> assertThat(creator.createEntityModel(resource, controller).getContent())
                        .isEqualToComparingFieldByField(resource)
        );
    }

    @Test
    void creatRepresentationModelTest() {
        assertThat(creator.createRepresentationModel(controller))
                .isInstanceOf(RepresentationModel.class);
    }
}