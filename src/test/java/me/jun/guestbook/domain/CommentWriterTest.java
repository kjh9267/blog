package me.jun.guestbook.domain;

import org.junit.jupiter.api.Test;

import static me.jun.guestbook.CommentFixture.commentWriter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CommentWriterTest {

    @Test
    void constructorTest() {
        CommentWriter commentWriter = commentWriter();

        CommentWriter expected = new CommentWriter(1L);

        assertAll(
                () -> assertThat(commentWriter).isInstanceOf(CommentWriter.class),
                () -> assertThat(commentWriter).isEqualToComparingFieldByField(expected)
        );
    }

    @Test
    void matchTest() {
        CommentWriter commentWriter = commentWriter();

        assertAll(
                () -> assertThat(commentWriter.match(1L)).isTrue(),
                () -> assertThat(commentWriter.match(2L)).isFalse()
        );
    }
}