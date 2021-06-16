package me.jun.guestbook.post.application;

import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
import me.jun.guestbook.post.application.exception.WriterMisMatchException;
import me.jun.guestbook.post.application.exception.PostNotFoundException;
import me.jun.guestbook.post.domain.Post;
import me.jun.guestbook.post.domain.PostRepository;
import me.jun.guestbook.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
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

    @Mock
    private GuestRepository guestRepository;

    private Post post;

    private Guest guest;

    private PostCreateRequest postCreateRequest;

    private PostUpdateRequest postUpdateRequest;

    private final Long postId = 1L;

    private final Long guestId = 1L;

    private final String guestName = "test user";

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

        guest = Guest.builder()
                .name(guestName)
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
    }

    @Test
    void readPostTest() {
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        PostResponse postResponse = postService.readPost(postId);

        assertAll(
//                () -> assertThat(postResponse.getWriter()).isEqualTo(guestName),
                () -> assertThat(postResponse.getTitle()).isEqualTo(title),
                () -> assertThat(postResponse.getContent()).isEqualTo(content)
        );
    }

    @Test
    void readPostFailTest() {
        given(postRepository.findById(postId)).willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class,
                () -> postService.readPost(postId)
        );
    }

    @Test
    void updatePostTest() {
        given(postRepository.findById(postId)).willReturn(Optional.of(post));
        given(postRepository.save(post)).willReturn(post);

        postService.updatePost(postUpdateRequest, guestId);
    }

    @Test
    void noPost_updatePostFailTest() {
        given(postRepository.findById(postId)).willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class,
                () -> postService.updatePost(postUpdateRequest, guestId)
        );
    }

    @Test
    void guestMismatch_updatePostFailTest() {
        given(postRepository.findById(any())).willReturn(Optional.of(post));

        assertThrows(WriterMisMatchException.class,
                () -> postService.updatePost(postUpdateRequest, 2L)
        );
    }

    @Test
    void readPostByPageTest() {

        // Given
        ManyPostRequestDto request = ManyPostRequestDto.builder()
                .page(0)
                .build();

        Page<Post> posts = new PageImpl<Post>(Arrays.asList(post, post, post));

        given(postRepository.findAll(PageRequest.of(0, 10)))
                .willReturn(posts);

        // When
        ManyPostResponseDto manyPostResponseDto = postService.readPostByPage(request);

        assertAll(
                () -> assertThat(manyPostResponseDto.getPostResponses().getTotalPages()).isEqualTo(1),
                () -> assertThat(manyPostResponseDto.getPostResponses().getTotalElements()).isEqualTo(3)
        );
    }
}
