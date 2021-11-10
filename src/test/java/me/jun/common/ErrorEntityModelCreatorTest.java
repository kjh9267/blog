package me.jun.common;

import me.jun.common.error.ErrorCode;
import me.jun.common.error.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(MockitoExtension.class)
class ErrorEntityModelCreatorTest {

    private ErrorEntityModelCreator creator;

    private ErrorResponse resource;

    @BeforeEach
    void setUp() {
        creator = new ErrorEntityModelCreator();
    }

    @Test
    void createErrorEntityModelTest() {
        resource = ErrorResponse.from(ErrorCode.GUEST_ALREADY_EXIST);

        EntityModel<ErrorResponse> errorEntityModel = creator.createErrorEntityModel(resource);

        assertAll(() -> assertThat(errorEntityModel)
                            .isInstanceOf(EntityModel.class),
                () -> assertThat(errorEntityModel.getContent())
                        .isEqualToComparingFieldByField(resource)
        );
    }
}