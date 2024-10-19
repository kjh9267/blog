package me.jun.core.guestbook.application;

import me.jun.core.guestbook.application.dto.PagedPostsResponse;
import me.jun.core.guestbook.application.dto.PostUpdateRequest;
import me.jun.core.guestbook.application.exception.PostNotFoundException;
import me.jun.core.guestbook.domain.Post;
import me.jun.core.guestbook.domain.PostWriter;
import me.jun.core.guestbook.domain.exception.PostWriterMismatchException;
import me.jun.core.guestbook.domain.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static me.jun.core.guestbook.PostCountFixture.postCount;
import static me.jun.core.guestbook.PostFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

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
    void createPostTest() {
        given(postRepository.save(any()))
                .willReturn(post());

        given(postCountService.createPostCount(any()))
                .willReturn(postCount());

        assertThat(postService.createPost(postCreateRequest()))
                .isEqualToComparingFieldByField(postResponse());
    }

    @Test
    void retrievePostTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.of(post()
                        .toBuilder()
                        .build()));

        assertThat(postService.retrievePost(POST_ID))
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
    void updatePostTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.of(post()));

        assertThat(postService.updatePost(postUpdateRequest()))
                .isEqualToComparingFieldByField(updatedPostResponse());
    }

    @Test
    void noPost_updatePostFailTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class,
                () -> postService.updatePost(postUpdateRequest())
        );
    }

    @Test
    void writerMismatch_updatePostFailTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.of(post()));

        PostUpdateRequest request = postUpdateRequest().toBuilder()
                .writerId(2L)
                .build();

        assertThrows(PostWriterMismatchException.class,
                () -> postService.updatePost(request)
        );
    }

    @Test
    void deletePostTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.of(post()));

        doNothing().when(commentService)
                .deleteCommentByPostId(any());

        doNothing().when(postRepository)
                .deleteById(any());

        postService.deletePost(postDeleteRequest());

        verify(commentService).deleteCommentByPostId(POST_ID);
        verify(postRepository).deleteById(POST_ID);
    }

    @Test
    void noPost_deletePostFailTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(
                PostNotFoundException.class,
                () -> postService.deletePost(postDeleteRequest())
        );
    }

    @Test
    void invalidWriter_deletePostFailTest() {
        Post post = post().toBuilder()
                .postWriter(new PostWriter(2L))
                .build();

        given(postRepository.findById(any()))
                .willReturn(Optional.of(post));

        assertThrows(
                PostWriterMismatchException.class,
                () -> postService.deletePost(postDeleteRequest())
        );
    }

    @Test
    void deletePostByWriterIdTest() {
        doNothing().when(postRepository)
                .deleteAllByPostWriter(postWriter());

        postService.deletePostByWriterId(POST_WRITER_ID);

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
