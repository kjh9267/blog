package me.jun.core.blog.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jun.core.blog.ArticleFixture;
import me.jun.core.blog.CategoryFixture;
import me.jun.core.blog.application.ArticleCategoryService;
import me.jun.core.blog.application.CategoryService;
import me.jun.core.blog.application.exception.CategoryNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArticleCategoryService articleCategoryService;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void retrieveCategoryArticlesTest() throws Exception {
        String expected = objectMapper.writeValueAsString(ArticleFixture.pagedArticleResponse());

        given(articleCategoryService.queryCategoryArticles(any(), any()))
                .willReturn(ArticleFixture.pagedArticleResponse());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/blog/category/" + CategoryFixture.CATEGORY_NAME + "?page=0&size=10")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expected));
    }

    @Test
    void noCategory_retrieveCategoryArticlesFailTest() throws Exception {
        given(articleCategoryService.queryCategoryArticles(any(), any()))
                .willThrow(new CategoryNotFoundException("python"));

        mockMvc.perform(get("/api/blog/category/python?page=0&size=10")
                        .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    void retrieveCategoriesTest() throws Exception {
        String expected = objectMapper.writeValueAsString(CategoryFixture.categoryListResponse());

        given(categoryService.retrieveCategories())
                .willReturn(CategoryFixture.categoryListResponse());

        mockMvc.perform(get("/api/blog/category")
                .accept(APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expected));
    }
}