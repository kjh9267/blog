package me.jun.guestbook.comment.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.jun.guestbook.comment.CommentFixture.comment;
import static me.jun.guestbook.comment.CommentFixture.commentWriter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CommentTest {

    @Mock
    private CommentWriter commentWriter;

    @Test
    void constructorTest() {
        Comment expected = Comment.builder()
                .id(1L)
                .postId(1L)
                .commentWriter(commentWriter())
                .content("test content")
                .build();

        assertAll(() -> assertThat(comment()).isInstanceOf(Comment.class),
                () -> assertThat(comment()).isEqualToComparingFieldByField(expected)
        );
    }

    @Test
    void validateWriterTest() {
        Comment comment = comment();
        comment.setCommentWriter(commentWriter);

        assertThrows(CommentWriterMismatchException.class,
                () -> comment.validateWriter(2L)
        );
    }
}
