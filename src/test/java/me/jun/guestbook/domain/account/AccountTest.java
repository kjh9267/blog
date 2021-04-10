package me.jun.guestbook.domain.account;

import me.jun.guestbook.domain.post.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountTest {

    private Account account;

    private Post post;

    String name = "jun";
    String title = "test title";
    String content = "test content";
    String email = "user@email.com";
    String password = "pass";

    @Before
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
    public void constructorTest() {
        assertThat(account).isInstanceOf(Account.class);
    }

    @Test
    public void constructorTest2() {
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
    public void addPostTest() {
        post.setAccount(account);

        assertThat(account.getPosts().contains(post)).isTrue();
    }

    @Test
    public void validateTest() {
        assertThrows(WrongPasswordException.class,
                () -> account.validate("123")
        );
    }
}
