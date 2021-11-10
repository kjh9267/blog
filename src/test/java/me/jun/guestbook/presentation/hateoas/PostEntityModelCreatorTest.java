package me.jun.guestbook.presentation.hateoas;

import me.jun.guestbook.application.dto.PostResponse;
import me.jun.guestbook.presentation.hateoas.PostEntityModelCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;

import static me.jun.guestbook.PostFixture.postResponse;
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

        EntityModel<PostResponse> entityModel = creator.createEntityModel(resource);

        assertAll(
                () -> assertThat(entityModel)
                        .isInstanceOf(EntityModel.class),
                () -> assertThat(entityModel.getContent())
                        .isEqualToComparingFieldByField(resource)
        );
    }

    @Test
    void creatRepresentationModelTest() {
        RepresentationModel representationModel = creator.createHyperlinks();

        assertThat(representationModel)
                .isInstanceOf(RepresentationModel.class);
    }
}