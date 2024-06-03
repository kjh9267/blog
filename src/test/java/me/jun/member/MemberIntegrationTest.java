
package me.jun.member;

import me.jun.support.IntegrationTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static me.jun.member.MemberFixture.adminRequest;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.NO_CONTENT;

public class MemberIntegrationTest extends IntegrationTest {

    @Test
    void memberTest() {
        register();
        token = login(adminRequest());
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
                .statusCode(NO_CONTENT.value());
    }
}