package me.jun.guestbook.domain.account;

import me.jun.guestbook.domain.post.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AccountTest {

    private Account account;

    private Post post;

    private final String name = "jun";
    private final String title = "test title";
    private final String content = "test content";
    private final String email = "user@email.com";
    private final String password = "pass";

    @BeforeEach
    public void setUp() {

        account = Account.builder()
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
        assertThat(account).isInstanceOf(Account.class);
    }

    @Test
    void constructorTest2() {
        Account newAccount = Account.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        assertAll(
                () -> assertThat(newAccount).isEqualToIgnoringGivenFields(account, "password", "id"),
                () -> assertThat(newAccount.getPassword()).isInstanceOf(Password.class),
                () -> assertThat(newAccount).isNotSameAs(account)
        );

    }

    @Test
    void addPostTest() {
        post.setAccount(account);

        assertThat(account.getPosts().contains(post)).isTrue();
    }

    @Test
    void validateTest() {
        assertThrows(WrongPasswordException.class,
                () -> account.validate("123")
        );
    }
}
