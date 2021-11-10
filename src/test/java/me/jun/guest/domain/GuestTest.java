package me.jun.guest.domain;

import me.jun.guest.domain.exception.WrongPasswordException;
import org.junit.jupiter.api.Test;

import static me.jun.guest.GuestFixture.guest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GuestTest {

    @Test
    void constructorTest() {
        Guest expected = Guest.builder()
                .id(1L)
                .name("test user")
                .email("testuser@email.com")
                .password("pass")
                .build();

        assertAll(() -> assertThat(guest()).isInstanceOf(Guest.class),
                () -> assertThat(guest()).isEqualToComparingFieldByField(expected)
        );
    }

    @Test
    void validateTest() {
        assertThrows(WrongPasswordException.class,
                () -> guest().validate("wrong password")
        );
    }
}
