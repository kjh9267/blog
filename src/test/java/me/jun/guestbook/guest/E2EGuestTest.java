package me.jun.guestbook.guest;

import io.restassured.http.Header;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static me.jun.guestbook.guest.GuestFixture.ACCESS_TOKEN;
import static me.jun.guestbook.guest.GuestFixture.guestRequest;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class E2EGuestTest {

    @LocalServerPort
    private int port;

    @Test
    void guestTest() {
        register();
        String token = login();
    }

    private void register() {
        given()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(guestRequest())

                .when()
                .post("/api/register")

                .then()
                .statusCode(CREATED.value());
    }

    private String login() {
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
