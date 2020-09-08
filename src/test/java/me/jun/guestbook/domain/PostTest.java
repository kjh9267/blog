package me.jun.guestbook.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RunWith(SpringRunner.class)
public class PostTest {

    private Post post;

    @Before
    public void setUp() {
        final String owner = "jun";
        final String password = "pass";
        final String title = "test title";
        final String content = "test content";

        post = Post.builder()
                .owner(owner)
                .password(password)
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
        final String owner = "jun";
        final String password = "pass";
        final String title = "test title";
        final String content = "test content";

        final Post post2 = Post.builder()
                .owner(owner)
                .password(password)
                .title(title)
                .content(content)
                .build();

        assertAll(
                () -> assertThat(post2.getOwner()).isEqualTo(owner),
                () -> assertThat(post2.getPassword()).isEqualTo(password),
                () -> assertThat(post2.getTitle()).isEqualTo(title),
                () -> assertThat(post2.getContent()).isEqualTo(content)
        );

        assertThat(post2).isEqualToComparingFieldByField(post);
    }
}
