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
        final String name = "jun";
        final String title = "test title";
        final String content = "test content";
        final String email = "user@email.com";
        final String password = "pass";

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
        account.addPost(post);

        assertThat(account.getPosts().contains(post)).isTrue();
        assertThat(post.getAccount()).isEqualTo(account);
    }
}
