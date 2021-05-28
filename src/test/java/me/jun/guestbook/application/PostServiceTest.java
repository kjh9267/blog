package me.jun.guestbook.application;

import me.jun.guestbook.application.exception.GuestNotFoundException;
import me.jun.guestbook.application.exception.PostNotFoundException;
import me.jun.guestbook.domain.guest.Guest;
import me.jun.guestbook.domain.guest.GuestRepository;
import me.jun.guestbook.domain.post.Post;
import me.jun.guestbook.domain.post.PostRepository;
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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private GuestRepository guestRepository;

    private Post post;

    private Guest guest;

    private PostRequest postRequest;

    private PostIdRequest postIdRequest;

    private final Long postId = 1L;

    private final Long guestId = 1L;

    private final String guestName = "test user";

    private final String title = "test title";

    private final String content = "test content";

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository, guestRepository);

        post = Post.builder()
                .id(1L)
                .title(title)
                .content(content)
                .guest(Guest.builder().name(guestName).build())
                .build();

        guest = Guest.builder()
                .name(guestName)
                .build();

        postRequest = PostRequest.builder()
                .id(postId)
                .title(title)
                .content(content)
                .build();

        postIdRequest = PostIdRequest.builder()
                .id(postId)
                .build();
    }

    @Test
    void readPostTest() {
        given(postRepository.findById(postId)).willReturn(Optional.of(post));

        PostResponse postResponse = postService.readPost(postIdRequest);

        assertAll(
                () -> assertThat(postResponse.getWriter()).isEqualTo(guestName),
                () -> assertThat(postResponse.getTitle()).isEqualTo(title),
                () -> assertThat(postResponse.getContent()).isEqualTo(content)
        );
    }

    @Test
    void readPostFailTest() {
        given(postRepository.findById(postId)).willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class,
                () -> postService.readPost(postIdRequest)
        );
    }

    @Test
    void createPostFailTest() {
        given(guestRepository.findById(guestId)).willReturn(Optional.empty());

        assertThrows(GuestNotFoundException.class,
                () -> postService.createPost(postRequest, guestId)
        );
    }

    @Test
    void updatePostTest() {
        given(postRepository.findById(postId)).willReturn(Optional.of(post));
        given(postRepository.save(post)).willReturn(post);

        lenient().when(postService.updatePost(postRequest))
                .thenReturn(PostResponse.builder()
                        .writer(guestName)
                        .title(title)
                        .content(content)
                        .build());
    }

    @Test
    void updatePostFailTest() {
        given(postRepository.findById(postId)).willReturn(Optional.empty());

        assertThrows(PostNotFoundException.class,
                () -> postService.updatePost(postRequest)
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
