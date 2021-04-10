package me.jun.guestbook.domain.account;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class PasswordTest {

    @Test
    void constructorTest() {
        Password password = new Password("123");

        assertThat(password).isInstanceOf(Password.class);
    }

    @Test
    void matchTest() {
        Password password = new Password("123");

        assertThat(password.match("456")).isFalse();
    }
}
