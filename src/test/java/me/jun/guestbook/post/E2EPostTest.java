package me.jun.guestbook.post;

import me.jun.guestbook.common.error.ErrorResponse;
import me.jun.guestbook.post.presentation.dto.PostResponse;
import me.jun.guestbook.support.E2ETest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static me.jun.guestbook.post.PostFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class E2EPostTest extends E2ETest {

    @Test
    void postTest() {
        register();
        token = login();

        createPost(token);
        retrievePost(token);
        updatePost(token);
        deletePost(token);
    }

    public void createPost(String token) {
        PostResponse postResponse = given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .body(postCreateRequest())

                .when()
                .post("/api/posts")

                .then()
                .statusCode(CREATED.value())
                .extract()
                .as(PostResponse.class);

        assertThat(postResponse).isEqualToComparingFieldByField(postResponse());
    }

    private void retrievePost(String token) {
        PostResponse postResponse = given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)

                .when()
                .get("/api/posts/1")

                .then()
                .statusCode(OK.value())
                .extract()
                .as(PostResponse.class);

        assertThat(postResponse).isEqualToComparingFieldByField(postResponse());
    }

    private void updatePost(String token) {
        PostResponse postResponse = given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .body(postUpdateRequest())

                .when()
                .put("/api/posts")

                .then()
                .extract()
                .as(PostResponse.class);

        assertThat(postResponse).isEqualToComparingFieldByField(updatedPostResponse());
    }

    private void deletePost(String token) {
        given()
                .port(port)
                .log().all()
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)

                .when()
                .delete("/api/posts/1")

                .then()
                .statusCode(OK.value());

        ErrorResponse errorResponse = given()
                .port(port)
                .log().all()
                .contentType(APPLICATION_JSON_VALUE)

                .when()
                .get("api/posts/1")

                .then()
                .statusCode(NOT_FOUND.value())
                .extract()
                .as(ErrorResponse.class);

        assertThat(errorResponse.getMessage()).isEqualTo("No post");
    }
}
