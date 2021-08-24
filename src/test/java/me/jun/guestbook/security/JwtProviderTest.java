package me.jun.guestbook.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JwtProviderTest {
    private String email = "testuser@email.com";

    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider(1_000 * 30L);
    }

    @Test
    void createJwtTest() {
        String jwt = jwtProvider.createJwt(email);

        assertThat(jwt.getClass()).isEqualTo(String.class);
        assertThat(jwt.split("\\.").length).isEqualTo(3);
    }

    @Test
    void extractSubjectTest() {
        String jwt = jwtProvider.createJwt(email);
        String subject = jwtProvider.extractSubject(jwt);

        assertThat(subject).isEqualTo(email);
    }

    @Test
    void wrongToken_validateTokenFailTest() {
        jwtProvider.createJwt(email);

        assertThrows(InvalidTokenException.class,
                () -> jwtProvider.validateToken("1.2.3")
        );
    }

    @Test
    void expiredToken_validateTokenFailTest() {
        jwtProvider = new JwtProvider(0L);

        String jwt = jwtProvider.createJwt(email);

        assertThrows(InvalidTokenException.class,
                () -> jwtProvider.validateToken(jwt)
        );
    }
}
