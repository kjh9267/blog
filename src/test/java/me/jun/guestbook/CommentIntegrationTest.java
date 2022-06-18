package me.jun.guestbook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.jun.guestbook.application.dto.CommentResponse;
import me.jun.support.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static me.jun.guestbook.CommentFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CommentIntegrationTest extends IntegrationTest {

    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Test
    void commentTest() {
        register();
        token = login();

        createComment(token, COMMENT_ID);
        retrieveComment();
        updateComment(token);
        deleteComment(token);
    }

    @Test
    void commentQueryTest() {
        register();
        token = login();

        for (long id = 1; id <= 30; id++) {
            createComment(token, id);
        }

        String response = given()
                .port(port)
                .log().all()
                .header(AUTHORIZATION, token)
                .queryParam("page", 2)
                .queryParam("size", 10)

                .when()
                .get("/api/guestbook/comments/query/post-id/1")

                .then()
                .statusCode(OK.value())
                .extract()
                .asString();

        JsonElement jsonElement = JsonParser.parseString(response);
        response = gson.toJson(jsonElement);

        System.out.println(response);
    }

    private void createComment(String token, Long commentId) {
        CommentResponse commentResponse = given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .body(commentCreateRequest())

                .when()
                .post("/api/guestbook/comments")

                .then()
                .statusCode(OK.value())
                .extract()
                .as(CommentResponse.class);

        assertThat(commentResponse)
                .isEqualToComparingFieldByField(
                        commentResponse().toBuilder()
                                .id(commentId)
                                .build()
                );
    }

    private void retrieveComment() {
        CommentResponse commentResponse = given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)

                .when()
                .get("/api/guestbook/comments/1")

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
                .put("/api/guestbook/comments")

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
                .delete("/api/guestbook/comments/1")

                .then()
                .statusCode(OK.value());

        given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)

                .when()
                .get("/api/guestbook/comments/1")

                .then()
                .statusCode(NOT_FOUND.value());
    }
}
