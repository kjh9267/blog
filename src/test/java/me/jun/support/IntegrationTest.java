package me.jun.support;

import me.jun.core.member.application.dto.LoginRequest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;
import static me.jun.core.member.MemberFixture.ACCESS_TOKEN;
import static me.jun.core.member.MemberFixture.adminRegisterRequest;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class IntegrationTest {

    @LocalServerPort
    protected int port;

    protected String token;

    protected void register() {
        given()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(adminRegisterRequest())

                .when()
                .post("/api/member/register")

                .then()
                .statusCode(OK.value());
    }

    protected String login(LoginRequest loginRequest) {
        return given()
                .port(port)
                .contentType(APPLICATION_JSON_VALUE)
                .body(loginRequest)

                .when()
                .post("/api/member/login")

                .then()
                .statusCode(OK.value())
                .assertThat().body("$", hasKey(ACCESS_TOKEN))
                .extract()
                .path(ACCESS_TOKEN);
    }
}
