package me.jun.core.guestbook.domain;

import org.junit.jupiter.api.Test;

import static me.jun.core.guestbook.PostFixture.postWriter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PostWriterTest {

    @Test
    void constructorTest() {
        PostWriter postWriter = postWriter();

        PostWriter expected = new PostWriter("testuser@email.com");

        assertAll(
                () -> assertThat(postWriter).isInstanceOf(PostWriter.class),
                () -> assertThat(postWriter).isEqualToComparingFieldByField(expected)
        );
    }

    @Test
    void matchTest() {
        PostWriter postWriter = postWriter();

        assertAll(
                () -> assertThat(postWriter.match("testuser@email.com")).isTrue(),
                () -> assertThat(postWriter.match("user@email.com")).isFalse()
        );
    }
}