package me.jun.core.blog;

import me.jun.core.blog.application.dto.ArticleResponse;
import me.jun.support.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;

import static io.restassured.RestAssured.given;
import static me.jun.core.blog.ArticleFixture.*;
import static me.jun.core.member.MemberFixture.adminLoginRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

class BlogIntegrationTest extends IntegrationTest {

    @Test
    @Rollback(value = false)
    void BlogTest() {
        register();
        String token = login(adminLoginRequest());

        createArticle(token);
        retrieveArticle(token, 1);
        updateArticle(token);
    }

    private void createArticle(String token) {
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
                .isEqualToComparingFieldByField(articleResponse());
    }

    private void retrieveArticle(String token, long id) {
        ArticleResponse articleResponse = given()
                .port(port)
                .log().all()
                .header(AUTHORIZATION, token)
                .contentType(APPLICATION_JSON_VALUE)
                .param(String.format("%d", id))

                .when()
                .get(String.format("/api/blog/articles/%d", id))

                .then()
                .statusCode(OK.value())
                .extract()
                .as(ArticleResponse.class);

        assertThat(articleResponse)
                .isEqualToComparingFieldByField(articleResponse());
    }

    private void updateArticle(String token) {
        ArticleResponse articleResponse = given()
                .log().all()
                .port(port)
                .header(AUTHORIZATION, token)
                .contentType(APPLICATION_JSON_VALUE)
                .body(articleUpdateRequest())

                .when()
                .put("/api/blog/articles")

                .then()
                .statusCode(OK.value())
                .extract()
                .as(ArticleResponse.class);

        assertThat(articleResponse)
                .isEqualToComparingFieldByField(updatedArticleResponse());
    }
}
