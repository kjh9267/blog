package me.jun.guestbook.post.presentation;

import me.jun.guestbook.post.presentation.dto.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import static me.jun.guestbook.post.PostFixture.postResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PostEntityModelCreatorTest {

    private PostEntityModelCreator creator;

    private PostResponse resource;

    @BeforeEach
    void setUp() {
        creator = new PostEntityModelCreator(); }

    @Test
    void createEntityModelTest() {
        resource = postResponse();

        EntityModel<PostResponse> entityModel = creator.createRepresentationModel(resource);

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