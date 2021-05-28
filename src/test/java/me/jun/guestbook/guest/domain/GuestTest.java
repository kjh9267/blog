package me.jun.guestbook.guest.domain;

import me.jun.guestbook.post.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GuestTest {

    private Guest guest;

    private Post post;

    private final String name = "jun";
    private final String title = "test title";
    private final String content = "test content";
    private final String email = "user@email.com";
    private final String password = "pass";

    @BeforeEach
    public void setUp() {

        guest = Guest.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        post = Post.builder()
                .title(title)
                .content(content)
                .build();
    }

    @Test
    void constructorTest() {
        assertThat(guest).isInstanceOf(Guest.class);
    }

    @Test
    void constructorTest2() {
        Guest newGuest = Guest.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        assertAll(
                () -> assertThat(newGuest).isEqualToIgnoringGivenFields(guest, "password", "id"),
                () -> assertThat(newGuest.getPassword()).isInstanceOf(Password.class),
                () -> assertThat(newGuest).isNotSameAs(guest)
        );

    }

    @Test
    void addPostTest() {
        post.setGuest(guest);

        assertThat(guest.getPosts().contains(post)).isTrue();
    }

    @Test
    void validateTest() {
        assertThrows(WrongPasswordException.class,
                () -> guest.validate("123")
        );
    }
}
