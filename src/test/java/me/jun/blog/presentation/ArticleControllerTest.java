package me.jun.blog.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.blog.application.ArticleService;
import me.jun.blog.application.ArticleWriterService;
import me.jun.blog.application.dto.ArticleCreateRequest;
import me.jun.blog.application.dto.ArticleInfoUpdateRequest;
import me.jun.common.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

import static me.jun.blog.ArticleFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureWebTestClient
public class ArticleControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private ArticleWriterService articleWriterService;

    private String jwt;

    @BeforeEach
    void setUp() {
        jwt = jwtProvider.createJwt(ARTICLE_WRITER_EMAIL);

        given(articleWriterService.retrieveArticleWriter(any()))
                .willReturn(CompletableFuture.completedFuture(
                        articleWriterInfo()
                ));
    }

    @Test
    void createArticleTest() throws Exception {
        String expected = objectMapper.writeValueAsString(articleResponse());

        given(articleService.createArticle(any(), any()))
                .willReturn(CompletableFuture.completedFuture(
                        articleResponse()
                ));

        webTestClient.post()
                .uri("/api/articles")
                .header(AUTHORIZATION, jwt)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(articleCreateRequest()), ArticleCreateRequest.class)
                .exchange()

                .expectStatus().is2xxSuccessful()
                .expectBody().json(expected);
    }

    @Test
    void retrieveArticleTest() throws Exception {
        String expected = objectMapper.writeValueAsString(articleResponse());

        given(articleService.retrieveArticle(any()))
                .willReturn(CompletableFuture.completedFuture(
                        articleResponse()
                ));

        webTestClient.get()
                .uri("/api/articles/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()

                .expectStatus().is2xxSuccessful()
                .expectBody().json(expected);
    }

    @Test
    void updateArticleTest() throws Exception {
        String expected = objectMapper.writeValueAsString(updatedArticleResponse());

        given(articleService.updateArticleInfo(any()))
                .willReturn(CompletableFuture.completedFuture(
                        updatedArticleResponse()
                ));

        webTestClient.put()
                .uri("/api/articles")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(AUTHORIZATION, jwt)
                .body(Mono.just(articleUpdateRequest()), ArticleInfoUpdateRequest.class)
                .exchange()

                .expectStatus().is2xxSuccessful()
                .expectBody().json(expected);
    }
}
