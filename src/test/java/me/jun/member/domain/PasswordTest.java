package me.jun.member.domain;

import org.junit.jupiter.api.Test;

import static me.jun.member.MemberFixture.PASSWORD;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class PasswordTest {

    @Test
    void constructorTest() {
        Password password = new Password(PASSWORD);

        assertThat(password).isInstanceOf(Password.class);
    }

    @Test
    void matchTest() {
        Password password = new Password(PASSWORD);

        assertThat(password.match("wrong password")).isFalse();
    }
}
