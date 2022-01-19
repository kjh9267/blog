package me.jun.blog.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.blog.application.ArticleCategoryService;
import me.jun.blog.application.exception.CategoryNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static me.jun.blog.ArticleFixture.pagedArticleResponse;
import static me.jun.blog.CategoryFixture.CATEGORY_NAME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void retrieveCategoryArticlesTest() throws Exception {
        String expected = objectMapper.writeValueAsString(pagedArticleResponse());

        given(articleCategoryService.queryCategoryArticles(any(), any()))
                .willReturn(pagedArticleResponse());

        mockMvc.perform(get("/api/category/" + CATEGORY_NAME + "?page=0&size=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expected));
    }

    @Test
    void noCategory_retrieveCategoryArticlesFailTest() throws Exception {
        given(articleCategoryService.queryCategoryArticles(any(), any()))
                .willThrow(new CategoryNotFoundException("python"));

        mockMvc.perform(get("/api/category/python?page=0&size=10")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}