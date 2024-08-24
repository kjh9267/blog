package me.jun.guestbook.domain;

import me.jun.guestbook.domain.exception.CommentWriterMismatchException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.jun.guestbook.CommentFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CommentTest {

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
        comment.updateCommentWriter(commentWriter);

        assertThrows(CommentWriterMismatchException.class,
                () -> comment.validateWriter("user@email.com")
        );
    }

    @Test
    void updateCommentWriterTest() {
        Comment comment = comment();
        CommentWriter newCommentWriter = new CommentWriter("user@email.com");

        Comment newComment = comment.updateCommentWriter(newCommentWriter);

        assertThat(newComment.getCommentWriter())
                .isEqualToComparingFieldByField(newCommentWriter);
    }

    @Test
    void updateContentTest() {
        Comment comment = comment();
        Comment newComment = comment.updateContent("new content");

        assertThat(newComment.getContent())
                .isEqualTo(NEW_CONTENT);
    }
}
