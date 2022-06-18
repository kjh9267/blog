
package me.jun.member;

import me.jun.support.IntegrationTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;

public class MemberIntegrationTest extends IntegrationTest {

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
                .delete("/api/member/leave")

                .then()
                .statusCode(OK.value());
    }
}