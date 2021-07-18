package me.jun.guestbook.comment.presentation;

import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import static me.jun.guestbook.comment.CommentFixture.commentResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
class CommentEntityModelCreatorTest {

    private CommentEntityModelCreator creator;

    private CommentResponse resource;

    @BeforeEach
    void setUp() {
        creator = new CommentEntityModelCreator();
    }

    @Test
    void createEntityModelTest() {
        resource = commentResponse();

        EntityModel<CommentResponse> entityModel = creator.createEntityModel(resource);

        assertAll(
                () -> assertThat(entityModel)
                        .isInstanceOf(EntityModel.class),
                () -> assertThat(entityModel.getContent())
                        .isEqualToComparingFieldByField(resource)
        );
    }

    @Test
    void creatRepresentationModelTest() {
        RepresentationModel representationModel = creator.createRepresentationModel();

        assertThat(representationModel)
                .isInstanceOf(RepresentationModel.class);
    }
}