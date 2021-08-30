package me.jun.guestbook.comment;

import me.jun.guestbook.comment.presentation.dto.CommentResponse;
import me.jun.guestbook.support.E2ETest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static me.jun.guestbook.comment.CommentFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class E2ECommentTest extends E2ETest {

    @Test
    void commentTest() {
        register();
        token = login();

        createComment(token);
        retrieveComment();
        updateComment(token);
        deleteComment(token);
    }

    private void createComment(String token) {
        CommentResponse commentResponse = given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .body(commentCreateRequest())

                .when()
                .post("/api/comments")

                .then()
                .statusCode(CREATED.value())
                .extract()
                .as(CommentResponse.class);

        assertThat(commentResponse)
                .isEqualToComparingFieldByField(CommentFixture.commentResponse());
    }

    private void retrieveComment() {
        CommentResponse commentResponse = given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)

                .when()
                .get("/api/comments/1")

                .then()
                .statusCode(OK.value())
                .extract()
                .as(CommentResponse.class);

        assertThat(commentResponse)
                .isEqualToComparingFieldByField(CommentFixture.commentResponse());
    }

    private void updateComment(String token) {
        CommentResponse commentResponse = given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .body(commentUpdateRequest())

                .when()
                .put("/api/comments")

                .then()
                .statusCode(OK.value())
                .extract()
                .as(CommentResponse.class);

        assertThat(commentResponse)
                .isEqualToComparingFieldByField(updatedCommentResponse());
    }

    private void deleteComment(String token) {
        given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)

                .when()
                .delete("/api/comments/1")

                .then()
                .statusCode(OK.value());

        given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)

                .when()
                .get("/api/comments/1")

                .then()
                .statusCode(NOT_FOUND.value());
    }
}
