package me.jun.guestbook.post.domain;

import org.junit.jupiter.api.Test;

import static me.jun.guestbook.post.PostFixture.postWriter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PostWriterTest {

    @Test
    void constructorTest() {
        PostWriter postWriter = postWriter();

        PostWriter expected = new PostWriter(1L);

        assertAll(
                () -> assertThat(postWriter).isInstanceOf(PostWriter.class),
                () -> assertThat(postWriter).isEqualToComparingFieldByField(expected)
        );
    }

    @Test
    void matchTest() {
        PostWriter postWriter = postWriter();

        assertAll(
                () -> assertThat(postWriter.match(1L)).isTrue(),
                () -> assertThat(postWriter.match(2L)).isFalse()
        );
    }
}