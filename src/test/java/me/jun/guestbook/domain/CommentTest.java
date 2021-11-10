package me.jun.guestbook.domain;

import me.jun.guestbook.domain.exception.CommentWriterMismatchException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.jun.guestbook.CommentFixture.comment;
import static me.jun.guestbook.CommentFixture.commentWriter;
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
