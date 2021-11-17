package me.jun.guestbook.domain;

import me.jun.guestbook.domain.exception.PostWriterMismatchException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.jun.guestbook.PostFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PostTest {

    @Mock
    private PostWriter postWriter;

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
                .postWriter(new PostWriter(WRITER_ID))
                .build();

        assertAll(() -> assertThat(expected).isEqualToComparingFieldByField(post()),
                () -> assertThat(expected).isInstanceOf(Post.class)
        );
    }

    @Test
    void validateWriterTest() {
        Post post = post();
        post.setPostWriter(postWriter);
        given(postWriter.match(anyLong()))
                .willReturn(false);

        assertThrows(PostWriterMismatchException.class,
                () -> post.validateWriter(2L)
        );
    }
}
