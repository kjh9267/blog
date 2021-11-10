package me.jun.member.domain;

import me.jun.member.domain.exception.WrongPasswordException;
import org.junit.jupiter.api.Test;

import static me.jun.member.MemberFixture.member;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MemberTest {

    @Test
    void constructorTest() {
        Member expected = Member.builder()
                .id(1L)
                .name("test user")
                .email("testuser@email.com")
                .password("pass")
                .build();

        assertAll(() -> assertThat(member()).isInstanceOf(Member.class),
                () -> assertThat(member()).isEqualToComparingFieldByField(expected)
        );
    }

    @Test
    void validateTest() {
        assertThrows(WrongPasswordException.class,
                () -> member().validate("wrong password")
        );
    }
}
