package me.jun.guestbook.post.application;

import me.jun.guestbook.post.application.exception.WriterMismatchException;
import me.jun.guestbook.post.application.exception.PostNotFoundException;
import me.jun.guestbook.post.domain.Post;
import me.jun.guestbook.post.domain.PostRepository;
import me.jun.guestbook.post.presentation.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    private PostService postService;

    @Mock
    private PostRepository postRepository;

    private Post post;

    private PostCreateRequest postCreateRequest;

    private PostUpdateRequest postUpdateRequest;

    private PostResponse postResponse;

    private final Long postId = 1L;

    private final Long guestId = 1L;

    private final String title = "test title";

    private final String content = "test content";

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository);

        post = Post.builder()
                .id(1L)
                .title(title)
                .content(content)
                .writerId(guestId)
                .build();

        postCreateRequest = PostCreateRequest.builder()
                .title(title)
                .content(content)
                .build();

        postUpdateRequest = PostUpdateRequest.builder()
                .id(postId)
                .title(title)
                .content(content)
                .build();

        postResponse = PostResponse.of(post);
    }

    @Test
    void createPostTest() {
        given(postRepository.save(any()))
                .willReturn(post);

        assertThat(postService.createPost(postCreateRequest, 1L))
                .isEqualToComparingFieldByField(postResponse);
    }

    @Test
    void readPostTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.of(post));

        assertThat(postService.readPost(postId))
                .isEqualToComparingFieldByField(postResponse);
    }

    @Test
    void readPostFailTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class,
                () -> postService.readPost(postId)
        );
    }

    @Test
    void updatePostTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.of(post));

        given(postRepository.save(any()))
                .willReturn(post);

        assertThat(postService.updatePost(postUpdateRequest, guestId))
                .isEqualToComparingFieldByField(postResponse);
    }

    @Test
    void noPost_updatePostFailTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class,
                () -> postService.updatePost(postUpdateRequest, guestId)
        );
    }

    @Test
    void writerMismatch_updatePostFailTest() {
        given(postRepository.findById(any()))
                .willReturn(Optional.of(post));

        assertThrows(WriterMismatchException.class,
                () -> postService.updatePost(postUpdateRequest, 2L)
        );
    }

    @Test
    void queryPostsTest() {
        given(postRepository.findAll(any(Pageable.class)))
                .willReturn(Page.empty());

        assertThat(postService.readPostsByPage(PageRequest.of(1, 3)))
                .isInstanceOf(PagedPostsResponse.class);
    }
}
