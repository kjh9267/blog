package me.jun.common.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static me.jun.common.security.KeyFixture.JWT_KEY;
import static me.jun.member.MemberFixture.EMAIL;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtProviderTest {

    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider(1_000 * 30L,
                JWT_KEY);
    }

    @Test
    void createJwtTest() {
        String jwt = jwtProvider.createJwt(EMAIL);

        assertThat(jwt.getClass()).isEqualTo(String.class);
        assertThat(jwt.split("\\.").length).isEqualTo(3);
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
