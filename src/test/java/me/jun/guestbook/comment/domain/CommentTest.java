package me.jun.guestbook.comment.domain;

import org.junit.jupiter.api.Test;

import static me.jun.guestbook.comment.CommentFixture.comment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CommentTest {

    @Test
    void constructorTest() {
        Comment expected = Comment.builder()
                .id(1L)
                .postId(1L)
                .writerId(1L)
                .content("test content")
                .build();

        assertAll(() -> assertThat(comment()).isInstanceOf(Comment.class),
                () -> assertThat(comment()).isEqualToComparingFieldByField(expected)
        );
    }
}
