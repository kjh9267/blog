package me.jun.guestbook.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.common.security.JwtProvider;
import me.jun.guestbook.PostFixture;
import me.jun.guestbook.application.PostCountService;
import me.jun.guestbook.application.PostService;
import me.jun.guestbook.application.dto.PagedPostsResponse;
import me.jun.guestbook.application.dto.PostCreateRequest;
import me.jun.guestbook.application.dto.PostUpdateRequest;
import me.jun.guestbook.application.exception.PostNotFoundException;
import me.jun.guestbook.domain.Post;
import me.jun.guestbook.domain.exception.PostWriterMismatchException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static me.jun.guestbook.PostFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@AutoConfigureWebTestClient
@SpringBootTest
public class

PostControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @MockBean
    private PostCountService postCountService;

    @Autowired
    private JwtProvider jwtProvider;

    private String jwt;

    @BeforeEach
    public void setUp() {
        jwt = jwtProvider.createJwt(WRITER_EMAIL);
    }

    @Test
    public void createPostTest() throws Exception {
        String expected = objectMapper.writeValueAsString(postResponse());

        given(postService.createPost(any(), any()))
                .willReturn(CompletableFuture.completedFuture(
                        postResponse()
                ));

        webTestClient.post()
                .uri("/api/posts")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .header(AUTHORIZATION, jwt)

                .body(Mono.just(postCreateRequest()), PostCreateRequest.class)
                .exchange()

                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody().json(expected);
    }

    @Test
    void invalidInput_createPostFailTest() throws Exception {
        PostCreateRequest request = PostCreateRequest.builder()
                .title("")
                .content(" ")
                .build();

        webTestClient.post()
                .uri("/api/posts")
                .header(AUTHORIZATION, jwt)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)

                .body(Mono.just(request), PostCreateRequest.class)
                .exchange()

                .expectStatus().is4xxClientError();
    }

    @Test
    public void retrievePostTest() throws Exception {
        String expected = objectMapper.writeValueAsString(postResponse());

        given(postService.retrievePost(any()))
                .willReturn(CompletableFuture.completedFuture(
                        postResponse()
                ));

        given(postCountService.updateHits(POST_ID))
                .willReturn(1L);

        webTestClient.get()
                .uri("/api/posts/1")
                .accept(APPLICATION_JSON)
                .exchange()

                .expectStatus().is2xxSuccessful()
                .expectBody().json(expected);
    }

    @Test
    void retrieveNoPostFailTest() throws Exception {
        given(postService.retrievePost(any()))
                .willThrow(new PostNotFoundException());

        webTestClient.get()
                .uri("/api/posts/1")
                .exchange()

                .expectStatus().is4xxClientError();
    }

    @Test
    public void updatePostTest() throws Exception {
        String expected = objectMapper.writeValueAsString(updatedPostResponse());

        given(postService.updatePost(any(), any()))
                .willReturn(CompletableFuture.completedFuture(
                        updatedPostResponse()
                ));

        webTestClient.put()
                .uri("/api/posts")
                .header(AUTHORIZATION, jwt)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(Mono.just(postUpdateRequest()), PostUpdateRequest.class)
                .exchange()

                .expectStatus().is2xxSuccessful()
                .expectBody().json(expected);
    }

    @Test
    void noPost_updatePostFailTest() throws Exception {
        doThrow(PostNotFoundException.class)
                .when(postService)
                .updatePost(any(), any());

        webTestClient.put()
                .uri("/api/posts")
                .header(AUTHORIZATION, jwt)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .body(Mono.just(postUpdateRequest()), PostUpdateRequest.class)
                .exchange()

                .expectStatus().is4xxClientError();
    }

    @Test
    void memberMisMatch_updatePostFailTest() throws Exception {
        PostUpdateRequest request = PostUpdateRequest.builder()
                .id(1L)
                .title("new title")
                .content("new content")
                .build();

        doThrow(PostWriterMismatchException.class)
                .when(postService)
                .updatePost(any(), any());

        webTestClient.put()
                .uri("/api/posts")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(Mono.just(request), PostUpdateRequest.class)
                .exchange()

                .expectStatus().is4xxClientError();
    }

    @Test
    void invalidInput_updatePostFailTest() throws Exception {
        PostUpdateRequest request = PostUpdateRequest.builder()
                .id(-123L)
                .title("")
                .content(" ")
                .build();

        webTestClient.put()
                .uri("/api/posts")
                .header(AUTHORIZATION, jwt)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(Mono.just(request), PostUpdateRequest.class)
                .exchange()

                .expectStatus().is4xxClientError();
    }

    @Test
    public void deletePostTest() throws Exception {
        given(postService.deletePost(any(), any()))
                .willReturn(CompletableFuture.completedFuture(POST_ID));

        webTestClient.delete()
                .uri("/api/posts/1")
                .header(AUTHORIZATION, jwt)
                .accept(APPLICATION_JSON)
                .exchange()

                .expectStatus().is2xxSuccessful();
    }

    @Test
    void noPost_deletePostFailTest() throws Exception {
        doThrow(PostNotFoundException.class)
                .when(postService)
                .deletePost(any(), any());

        webTestClient.delete()
                .uri("/api/posts/2")
                .exchange()

                .expectStatus().is4xxClientError();
    }

    @Test
    void memberMisMatch_deletePostFailTest() throws Exception {
        doThrow(PostWriterMismatchException.class)
                .when(postService)
                .deletePost(any(), any());

        webTestClient.delete()
                .uri("/api/posts/1")
                .exchange()

                .expectStatus().is4xxClientError();
    }

    @Test
    void queryPostsTest() throws Exception {
        PagedPostsResponse response = PagedPostsResponse.from(new PageImpl<Post>(
                Arrays.asList(PostFixture.post())));

        String expected = objectMapper.writeValueAsString(response);

        given(postService.queryPosts(any()))
                .willReturn(response);

        webTestClient.get()
                .uri("/api/posts/query/?page=1&size=10")
                .accept(APPLICATION_JSON)
                .exchange()

                .expectStatus().is2xxSuccessful();
    }

    private Key getKey() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> resolver = Class.forName("me.jun.common.security.JwtProvider");
        Field secret_key = resolver.getDeclaredField("SECRET_KEY");
        secret_key.setAccessible(true);
        return (Key) secret_key.get(Key.class);
    }
}