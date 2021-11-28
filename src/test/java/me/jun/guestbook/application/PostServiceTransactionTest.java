package me.jun.guestbook.application;

import me.jun.guestbook.domain.repository.PostCountRepository;
import me.jun.support.E2ETest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static me.jun.guestbook.PostFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class PostServiceTransactionTest extends E2ETest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostCountRepository postCountRepository;

    @BeforeEach
    void setUp() {
        register();
        token = login();
        postService.createPost(postCreateRequest(), WRITER_EMAIL);
    }

    @Test
    void hitsTransactionTest() {
        for (int request = 0; request < 100; request++) {
            given()
                    .log().all()
                    .port(port)
                    .contentType(APPLICATION_JSON_VALUE)
                    .when()
                    .get("/api/posts/1");
        }

        assertThat(
                postCountRepository.findByPostId(POST_ID)
                        .get()
                        .getHits()
                        .getValue()
        )
                .isEqualTo(100L);
    }
}
