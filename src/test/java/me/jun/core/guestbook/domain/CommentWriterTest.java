package me.jun.core.guestbook.domain;

import org.junit.jupiter.api.Test;

import static me.jun.core.guestbook.CommentFixture.COMMENT_WRITER_ID;
import static me.jun.core.guestbook.CommentFixture.commentWriter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CommentWriterTest {

    @Test
    void constructorTest() {
        CommentWriter commentWriter = commentWriter();

        CommentWriter expected = new CommentWriter(COMMENT_WRITER_ID);

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