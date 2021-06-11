package me.jun.guestbook.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JwtProviderTest {
    private String email = "testuser@email.com";

    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProvider = new JwtProvider();
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
}
