package me.jun.blog.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.blog.application.ArticleService;
import me.jun.blog.application.ArticleWriterService;
import me.jun.common.security.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static me.jun.blog.ArticleFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @MockBean
    private ArticleWriterService articleWriterService;

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

        given(articleWriterService.retrieveArticleWriter(any()))
                .willReturn(articleWriterInfo());

        mockMvc.perform(
                post("/api/articles")
                        .content(content)
                        .header(AUTHORIZATION, jwt)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expected));
    }
}
