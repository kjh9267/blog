package me.jun.blog.application;

import me.jun.blog.application.dto.PagedArticleResponse;
import me.jun.blog.domain.Article;
import me.jun.blog.domain.Category;
import me.jun.blog.domain.repository.ArticleRepository;
import me.jun.blog.domain.service.CategoryMatchingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static me.jun.blog.ArticleFixture.*;
import static me.jun.blog.CategoryFixture.CATEGORY_NAME;
import static me.jun.blog.CategoryFixture.category;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArticleCategoryServiceTest {

    private ArticleCategoryService articleCategoryService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private CategoryMatchingService categoryMatchingService;

    @BeforeEach
    void setUp() {
        articleCategoryService = new ArticleCategoryService(
                articleRepository,
                categoryService,
                categoryMatchingService
        );
    }

    @Test
    void updateCategoryOfArticleTest() {
        // Given

        Article article = article();
        Category oldCategory = category();
        Category newCategory = category()
                .toBuilder()
                .id(2L)
                .name("spring")
                .build();

        given(articleRepository.findById(any()))
                .willReturn(Optional.of(article));

        given(categoryService.createCategoryOrElseGet(any()))
                .willReturn(newCategory);

        given(categoryService.retrieveCategoryById(any()))
                .willReturn(oldCategory);

        doNothing()
                .when(categoryMatchingService)
                .changeMatch(any(), any(), any());

        // When

        articleCategoryService.updateCategoryOfArticle(articleUpdateRequest());

        // Then

        verify(categoryMatchingService).changeMatch(article, newCategory, oldCategory);
    }

    @Test
    void queryCategoryArticlesTest() {
        given(categoryService.retrieveCategoryByName(any()))
                .willReturn(category());

        given(articleRepository.findByCategoryId(any(), any()))
                .willReturn(pagedArticle());

        PagedArticleResponse pagedArticleResponse = articleCategoryService.queryCategoryArticles(CATEGORY_NAME, PageRequest.of(0, 10));

        assertThat(pagedArticleResponse.getArticleResponses().getSize())
                .isEqualTo(10);
    }
}