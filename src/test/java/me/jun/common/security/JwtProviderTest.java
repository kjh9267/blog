package me.jun.common.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static me.jun.common.security.KeyFixture.JWT_KEY;
import static me.jun.core.member.MemberFixture.EMAIL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtProviderTest {

    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider(1_000 * 30L,
                JWT_KEY);
    }

    @Test
    void createJwtTest() {
        int expected = 3;
        String jwt = jwtProvider.createJwt(EMAIL);

        assertAll(
                () -> assertThat(jwt.getClass()).isEqualTo(String.class),
                () -> assertThat(jwt.split("\\.").length).isEqualTo(expected)
        );
    }

    @Test
    void extractSubjectTest() {
        String jwt = jwtProvider.createJwt(EMAIL);
        String subject = jwtProvider.extractSubject(jwt);

        assertThat(subject).isEqualTo(EMAIL);
    }

    @Test
    void wrongToken_validateTokenFailTest() {
        jwtProvider.createJwt(EMAIL);

        assertThrows(InvalidTokenException.class,
                () -> jwtProvider.validateToken("1.2.3")
        );
    }

    @Test
    void expiredToken_validateTokenFailTest() {
        jwtProvider = new JwtProvider(0L,
                JWT_KEY);

        String jwt = jwtProvider.createJwt(EMAIL);

        assertThrows(InvalidTokenException.class,
                () -> jwtProvider.validateToken(jwt)
        );
    }
}
