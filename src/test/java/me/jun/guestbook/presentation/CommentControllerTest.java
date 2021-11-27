package me.jun.guestbook.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.guestbook.application.CommentService;
import me.jun.guestbook.application.CommentWriterService;
import me.jun.guestbook.application.dto.CommentCreateRequest;
import me.jun.guestbook.application.dto.CommentUpdateRequest;
import me.jun.guestbook.domain.Comment;
import me.jun.guestbook.application.dto.PagedCommentsResponse;
import me.jun.common.security.JwtProvider;
import org.apache.http.auth.AUTH;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static me.jun.guestbook.CommentFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWebTestClient
public class CommentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentService commentService;

    @MockBean
    private CommentWriterService commentWriterService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    private String jwt;

    @BeforeEach
    void setUp() {
        jwt = jwtProvider.createJwt(EMAIL);
    }

    @Test
    void createCommentTest() throws Exception {
        String expected = objectMapper.writeValueAsString(commentResponse());

        given(commentService.createComment(any(), any()))
                .willReturn(CompletableFuture.completedFuture(
                        commentResponse()
                ));

        given(commentWriterService.retrieveCommentWriterBy(any()))
                .willReturn(
                        CompletableFuture.completedFuture(
                                commentWriterInfo()
                        )
                );

        webTestClient.post()
                .uri("/api/comments")
                .header(AUTHORIZATION, jwt)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(Mono.just(commentCreateRequest()), CommentCreateRequest.class)
                .exchange()

                .expectStatus().is2xxSuccessful()
                .expectBody().json(expected);
    }

    @Test
    void retrieveCommentTest() throws Exception {
        String expected = objectMapper.writeValueAsString(commentResponse());

        given(commentService.retrieveComment(any()))
                .willReturn(CompletableFuture.completedFuture(
                        commentResponse()
                ));

        webTestClient.get()
                .uri("/api/comments/1")
                .header(AUTHORIZATION, jwt)
                .accept(APPLICATION_JSON)
                .exchange()

                .expectStatus().is2xxSuccessful()
                .expectBody().json(expected);
    }

    @Test
    void updateCommentTest() throws Exception {
        given(commentWriterService.retrieveCommentWriterBy(any()))
                .willReturn(
                        CompletableFuture.completedFuture(
                                commentWriterInfo()
                        )
                );

        String expected = objectMapper.writeValueAsString(commentResponse());

        given(commentService.updateComment(any(), any()))
                .willReturn(CompletableFuture.completedFuture(
                        commentResponse()
                ));

        webTestClient.put()
                .uri("/api/comments")
                .header(AUTHORIZATION, jwt)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .body(Mono.just(commentUpdateRequest()), CommentUpdateRequest.class)
                .exchange()

                .expectStatus().is2xxSuccessful()
                .expectBody().json(expected);
    }

    @Test
    void deleteCommentTest() throws Exception {
        given(commentService.deleteComment(any(), any()))
                .willReturn(CompletableFuture.completedFuture(COMMENT_ID));

        given(commentWriterService.retrieveCommentWriterBy(any()))
                .willReturn(
                        CompletableFuture.completedFuture(
                                commentWriterInfo()
                        )
                );

        webTestClient.delete()
                .uri("/api/comments/1")
                .header(AUTHORIZATION, jwt)
                .accept(APPLICATION_JSON)
                .exchange()

                .expectStatus().is2xxSuccessful();

        verify(commentService).deleteComment(any(), any());
    }

    @Test
    void queryCommentsByPostIdTest() throws Exception {
        PagedCommentsResponse response = PagedCommentsResponse.from(new PageImpl<Comment>(
                Arrays.asList(comment())));

        given(commentService.queryCommentsByPostId(any(), any()))
                .willReturn(response);

        webTestClient.get()
                .uri("/api/comments/query/post-id/1?page=1&size=10")
                .accept(APPLICATION_JSON)
                .exchange()

                .expectStatus().is2xxSuccessful();
    }
}
