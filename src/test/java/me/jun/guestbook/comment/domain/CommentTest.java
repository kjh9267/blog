package me.jun.guestbook.comment.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CommentTest {

    @Test
    void constructorTest() {
        Comment comment = Comment.builder()
                .id(1L)
                .postId(1L)
                .content("test content")
                .build();

        assertThat(comment).isInstanceOf(Comment.class);
    }
}
