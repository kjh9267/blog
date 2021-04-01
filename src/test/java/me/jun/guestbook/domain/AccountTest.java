package me.jun.guestbook.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountTest {

    private Account account;

    private Post post;

    @Before
    public void setUp() {
        String name = "jun";
        String title = "test title";
        String content = "test content";
        String email = "user@email.com";
        String password = "pass";

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
    public void addPostTest() {
        post.setAccount(account);

        assertThat(account.getPosts().contains(post)).isTrue();
    }
}
