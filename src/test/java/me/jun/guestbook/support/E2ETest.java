package me.jun.guestbook.support;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static me.jun.guestbook.guest.GuestFixture.ACCESS_TOKEN;
import static me.jun.guestbook.guest.GuestFixture.guestRequest;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class E2ETest {

    @LocalServerPort
    protected int port;

    protected String token;

    protected void register() {
        given()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(guestRequest())

                .when()
                .post("/api/register")

                .then()
                .statusCode(CREATED.value());
    }

    protected String login() {
        return given()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(guestRequest())

                .when()
                .post("/api/login")

                .then()
                .statusCode(CREATED.value())
                .assertThat().body("$", hasKey(ACCESS_TOKEN))
                .extract()
                .path(ACCESS_TOKEN);
    }
}