package me.jun.guestbook.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(SpringRunner.class)
public class PostTest {

    private Post post;

    private Account account;

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
        final Post post = new Post();

        assertThat(post).isInstanceOf(Post.class);
    }

    @Test
    public void constructorTest2() {
        final String title = "test title";
        final String content = "test content";

        final Post post2 = Post.builder()
                .title(title)
                .content(content)
                .build();

        assertAll(
                () -> assertThat(post2.getTitle()).isEqualTo(title),
                () -> assertThat(post2.getContent()).isEqualTo(content)
        );

        assertThat(post2).isEqualToComparingFieldByField(post);
    }
}
