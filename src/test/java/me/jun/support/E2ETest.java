package me.jun.support;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static me.jun.member.MemberFixture.ACCESS_TOKEN;
import static me.jun.member.MemberFixture.memberRequest;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class E2ETest {

    @LocalServerPort
    protected int port;

    protected String token;

    protected void register() {
        given()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(memberRequest())

                .when()
                .post("/api/register")

                .then()
                .statusCode(OK.value());
    }

    protected String login() {
        return given()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(memberRequest())

                .when()
                .post("/api/login")

                .then()
                .statusCode(OK.value())
                .assertThat().body("$", hasKey(ACCESS_TOKEN))
                .extract()
                .path(ACCESS_TOKEN);
    }
}
