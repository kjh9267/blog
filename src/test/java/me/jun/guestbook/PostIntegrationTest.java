package me.jun.guestbook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.jun.common.error.ErrorResponse;
import me.jun.guestbook.application.dto.PostResponse;
import me.jun.support.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static me.jun.guestbook.PostFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class PostIntegrationTest extends IntegrationTest {

    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Test
    void postTest() {
        register();
        token = login();

        createPost(token, POST_ID);
        retrievePost(token);
        updatePost(token);
        deletePost(token);
    }

    @Test
    void postQueryTest() {
        register();
        token = login();

        for (long id = 1; id <= 30; id++) {
            createPost(token, id);
        }

        String response = given()
                .log().all()
                .port(port)
                .header(AUTHORIZATION, this.token)
                .contentType(APPLICATION_JSON_VALUE)
                .queryParam("page", 2 )
                .queryParam("size", 10)

                .when()
                .get("api/guestbook/posts/query")

                .then()
                .statusCode(OK.value())
                .extract()
                .asString();

        JsonElement jsonElement = JsonParser.parseString(response);
        response = gson.toJson(jsonElement);

        System.out.println(response);
    }

    public void createPost(String token, Long postId) {
        PostResponse postResponse = given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .body(postCreateRequest())

                .when()
                .post("/api/guestbook/posts")

                .then()
                .statusCode(OK.value())
                .extract()
                .as(PostResponse.class);

        assertThat(postResponse)
                .isEqualToComparingFieldByField(
                        postResponse().toBuilder()
                                .id(postId)
                                .build()
                );
    }

    private void retrievePost(String token) {
        PostResponse postResponse = given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)

                .when()
                .get("/api/guestbook/posts/1")

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
                .put("/api/guestbook/posts")

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
                .delete("/api/guestbook/posts/1")

                .then()
                .statusCode(OK.value());

        ErrorResponse errorResponse = given()
                .port(port)
                .log().all()
                .contentType(APPLICATION_JSON_VALUE)

                .when()
                .get("api/guestbook/posts/1")

                .then()
                .statusCode(NOT_FOUND.value())
                .extract()
                .as(ErrorResponse.class);
    }
}