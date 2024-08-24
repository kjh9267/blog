package me.jun.common.hateoas;

import me.jun.blog.presentation.TagController;
import me.jun.guestbook.application.dto.PostResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static me.jun.guestbook.PostFixture.postResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class DefaultLinkCreatorTest {

    @Autowired
    private LinkCreator linkCreator;

    @Test
    void createLinkTest() {
        String expected = "<http://localhost/api/blog/tag>;rel=\"self\"";

        PostResponse response = postResponse();
        linkCreator.createLink(TagController.class, response);

        assertThat(response.getLink("self").get().toString())
                .isEqualTo(expected);
    }
}