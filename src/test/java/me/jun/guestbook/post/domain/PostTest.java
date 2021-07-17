package me.jun.guestbook.post.domain;

import org.junit.jupiter.api.Test;

import static me.jun.guestbook.post.PostFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PostTest {

    @Test
    public void constructorTest() {
        Post post = new Post();

        assertThat(post).isInstanceOf(Post.class);
    }

    @Test
    public void constructorTest2() {
        Post expected = Post.builder()
                .id(POST_ID)
                .title(TITLE)
                .content(CONTENT)
                .writerId(WRITER_ID)
                .build();

        assertAll(() -> assertThat(expected).isEqualToComparingFieldByField(post()),
                () -> assertThat(expected).isInstanceOf(Post.class)
        );
    }
}
