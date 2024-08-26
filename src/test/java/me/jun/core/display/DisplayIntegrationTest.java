package me.jun.core.display;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.jun.core.blog.application.dto.ArticleResponse;
import me.jun.support.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static me.jun.core.blog.ArticleFixture.articleCreateRequest;
import static me.jun.core.blog.ArticleFixture.articleResponse;
import static me.jun.core.member.MemberFixture.adminLoginRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class DisplayIntegrationTest extends IntegrationTest {

    private Gson gson;

    @BeforeEach
    void setUp() {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    @Test
    void displayTest() {
        register();
        String token = login(adminLoginRequest());

        for (int article_id = 1; article_id < 11; article_id++) {
            createArticle(token, article_id);
        }

        String response = given()
                .log().all()
                .port(port)
                .queryParam("page", 0)
                .queryParam("size", 10)

                .when()
                .get("/api/display")

                .then()
                .statusCode(OK.value())
                .extract()
                .asString();

        JsonElement jsonElement = JsonParser.parseString(response);
        response = gson.toJson(jsonElement);
        System.out.println(response);
    }

    private void createArticle(String token, long articleId) {
        ArticleResponse articleResponse = given()
                .log().all()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, token)
                .body(articleCreateRequest())

                .when()
                .post("api/blog/articles")

                .then()
                .statusCode(CREATED.value())
                .extract()
                .as(ArticleResponse.class);

        assertThat(articleResponse)
                .isEqualToComparingFieldByField(
                        articleResponse().toBuilder()
                                .articleId(articleId)
                                .build()
                );
    }
}
