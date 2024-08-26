package me.jun.core.member.domain;

import me.jun.core.member.domain.exception.WrongPasswordException;
import org.junit.jupiter.api.Test;

import static me.jun.core.member.MemberFixture.member;
import static me.jun.core.member.domain.Role.USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberTest {

    @Test
    void constructorTest() {
        Member expected = Member.builder()
                .id(1L)
                .name("test user")
                .email("testuser@email.com")
                .password(new Password("pass"))
                .role(USER)
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
