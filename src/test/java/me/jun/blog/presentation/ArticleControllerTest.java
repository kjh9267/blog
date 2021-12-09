package me.jun.blog.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.blog.application.ArticleService;
import me.jun.common.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static me.jun.blog.ArticleFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @MockBean
    private ArticleService articleService;

    private String jwt;

    @BeforeEach
    void setUp() {
        jwt = jwtProvider.createJwt(ARTICLE_WRITER_EMAIL);
    }

    @Test
    void createArticleTest() throws Exception {
        String expected = objectMapper.writeValueAsString(articleResponse());

        String content = objectMapper.writeValueAsString(articleCreateRequest());

        given(articleService.createArticle(any(), any()))
                .willReturn(articleResponse());

        mockMvc.perform(
                        post("/api/blog/articles")
                                .content(content)
                                .header(AUTHORIZATION, jwt)
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expected));
    }

    @Test
    void retrieveArticleTest() throws Exception {
        String expected = objectMapper.writeValueAsString(articleResponse());

        given(articleService.retrieveArticle(any()))
                .willReturn(articleResponse());

        mockMvc.perform(get("/api/blog/articles/1"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expected));
    }

    @Test
    void updateArticleTest() throws Exception {
        String expected = objectMapper.writeValueAsString(updatedArticleResponse());

        String content = objectMapper.writeValueAsString(articleUpdateRequest());

        given(articleService.updateArticleInfo(any()))
                .willReturn(updatedArticleResponse());

        mockMvc.perform(
                        put("/api/blog/articles")
                                .contentType(APPLICATION_JSON)
                                .content(content)
                                .header(AUTHORIZATION, jwt)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expected));
    }

    @Test
    void queryPostsTest() throws Exception {
        String expected = objectMapper.writeValueAsString(pagedArticleResponse());

        given(articleService.queryArticles(any()))
                .willReturn(pagedArticleResponse());

        mockMvc.perform(get("/api/blog/articles/query?page=0&size=10")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expected));
    }
}