package me.jun.core.blog.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.common.security.JwtProvider;
import me.jun.core.blog.ArticleFixture;
import me.jun.core.blog.application.ArticleService;
import me.jun.core.blog.application.dto.ArticleCreateRequest;
import me.jun.core.blog.application.exception.ArticleNotFoundException;
import me.jun.core.member.MemberFixture;
import me.jun.core.member.application.MemberService;
import me.jun.core.member.application.dto.MemberResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static me.jun.core.blog.ArticleFixture.ARTICLE_WRITER_EMAIL;
import static me.jun.core.member.domain.Role.ADMIN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ArticleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @MockBean
    private ArticleService articleService;

    @MockBean
    private MemberService memberService;

    private String jwt;

    @BeforeEach
    void setUp() {
        jwt = jwtProvider.createJwt(ARTICLE_WRITER_EMAIL);

        MemberResponse admin = MemberFixture.memberResponse().toBuilder()
                .role(ADMIN)
                .build();

        given(memberService.retrieveMemberBy(any()))
                .willReturn(admin);
    }

    @Test
    void createArticleTest() throws Exception {
        String content = objectMapper.writeValueAsString(ArticleFixture.articleCreateRequest());

        given(articleService.createArticle(any()))
                .willReturn(ArticleFixture.articleResponse());

        ResultActions resultActions = mockMvc.perform(
                        post("/api/blog/articles")
                                .content(content)
                                .header(AUTHORIZATION, jwt)
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print());

        ArticleFixture.expectedJson(resultActions);
    }

    @Test
    void retrieveArticleTest() throws Exception {
        given(articleService.retrieveArticle(any()))
                .willReturn(ArticleFixture.articleResponse());

        ResultActions resultActions = mockMvc.perform(get("/api/blog/articles/1"))
                .andDo(print());

        ArticleFixture.expectedJson(resultActions);
    }

    @Test
    void noArticle_retrieveArticleFailTest() throws Exception {
        given(articleService.retrieveArticle(any()))
                .willThrow(new ArticleNotFoundException(ArticleFixture.ARTICLE_ID));

        mockMvc.perform(get("/api/blog/articles/2"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateArticleTest() throws Exception {
        String content = objectMapper.writeValueAsString(ArticleFixture.articleUpdateRequest());

        given(articleService.updateArticleInfo(any()))
                .willReturn(ArticleFixture.updatedArticleResponse());

        mockMvc.perform(
                        put("/api/blog/articles")
                                .contentType(APPLICATION_JSON)
                                .content(content)
                                .header(AUTHORIZATION, jwt)
                )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("_links").exists())
                .andExpect(jsonPath("article_id").value(ArticleFixture.ARTICLE_ID))
                .andExpect(jsonPath("title").value(ArticleFixture.NEW_TITLE))
                .andExpect(jsonPath("content").value(ArticleFixture.NEW_CONTENT));
    }

    @Test
    void noArticle_updateArticleFailTest() throws Exception {
        String content = objectMapper.writeValueAsString(ArticleFixture.articleUpdateRequest());

        given(articleService.updateArticleInfo(any()))
                .willThrow(new ArticleNotFoundException(ArticleFixture.ARTICLE_ID));

        mockMvc.perform(put("/api/blog/articles")
                .header(AUTHORIZATION, jwt)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void queryPostsTest() throws Exception {
        String expected = objectMapper.writeValueAsString(ArticleFixture.pagedArticleResponse());

        given(articleService.queryArticles(any()))
                .willReturn(ArticleFixture.pagedArticleResponse());

        mockMvc.perform(get("/api/blog/articles/query?page=0&size=10")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expected));
    }

    @Test
    void InvalidInputTest() throws Exception {
        String content = objectMapper.writeValueAsString(
                ArticleCreateRequest.builder()
                        .title("     ")
                        .content(" ")
                        .categoryName("")
                        .build()
        );

        mockMvc.perform(put("/api/blog/articles")
                .header(AUTHORIZATION, jwt)
                .contentType(APPLICATION_JSON)
                .content(content)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}