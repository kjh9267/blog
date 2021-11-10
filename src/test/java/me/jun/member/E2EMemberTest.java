
package me.jun.member;

import me.jun.support.E2ETest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;

public class E2EMemberTest extends E2ETest {

    @Test
    void memberTest() {
        register();
        token = login();
        leave();
    }

    private void leave() {
        given()
                .log().all()
                .port(port)
                .header(AUTHORIZATION, token)

                .when()
                .delete("/api/leave")

                .then()
                .statusCode(OK.value());
    }
}