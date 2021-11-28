package me.jun.guestbook.application;

import me.jun.guestbook.application.dto.PagedPostsResponse;
import me.jun.guestbook.application.exception.PostNotFoundException;
import me.jun.guestbook.domain.exception.PostWriterMismatchException;
import me.jun.guestbook.domain.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static me.jun.guestbook.PostCountFixture.postCount;
import static me.jun.guestbook.PostFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentService commentService;

    @Mock
    private PostCountService postCountService;

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository, commentService, postCountService);
    }

    @Test
    void createPostTest() throws ExecutionException, InterruptedException {
        given(postRepository.save(any()))
                .willReturn(post());

        given(postCountService.createPostCount(any()))
                .willReturn(postCount());

        assertThat(postService.createPost(postCreateRequest(), WRITER_EMAIL).get())
                .isEqualToComparingFieldByField(postResponse());
    }

    @Test
    void retrievePostTest() throws ExecutionException, InterruptedException {
        given(postRepository.findById(any()))
                .willReturn(Optional.of(post()
                        .toBuilder()
                        .build()));

        assertThat(postService.retrievePost(POST_ID).get())
                .isEqualToComparingFieldByField(postResponse());
    }

    @Test
    void readPostFailTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class,
                () -> postService.retrievePost(POST_ID)
        );
    }

    @Test
    void updatePostTest() throws ExecutionException, InterruptedException {
        given(postRepository.findById(any()))
                .willReturn(Optional.of(post()));

        assertThat(postService.updatePost(postUpdateRequest(), WRITER_EMAIL).get())
                .isEqualToComparingFieldByField(updatedPostResponse());
    }

    @Test
    void noPost_updatePostFailTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class,
                () -> postService.updatePost(postUpdateRequest(), WRITER_EMAIL)
        );
    }

    @Test
    void writerMismatch_updatePostFailTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.of(post()));

        assertThrows(PostWriterMismatchException.class,
                () -> postService.updatePost(postUpdateRequest(), "user@email.com")
        );
    }

    @Test
    void deletePostTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.of(post()));
        doNothing().when(postRepository)
                .deleteById(any());

        postService.deletePost(POST_ID, WRITER_EMAIL);

        verify(postRepository).deleteById(POST_ID);
        verify(commentService).deleteCommentByPostId(POST_ID);
    }

    @Test
    void deletePostByWriterIdTest() {
        doNothing().when(postRepository)
                .deleteAllByPostWriter(postWriter());

        postService.deletePostByWriterEmail(WRITER_EMAIL);

        verify(postRepository).deleteAllByPostWriter(postWriter());
    }

    @Test
    void queryPostsTest() {
        given(postRepository.findAll(any(Pageable.class)))
                .willReturn(Page.empty());

        assertThat(postService.queryPosts(PageRequest.of(1, 3)))
                .isInstanceOf(PagedPostsResponse.class);
    }
}
