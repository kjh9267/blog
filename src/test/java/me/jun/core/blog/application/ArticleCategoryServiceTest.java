package me.jun.core.blog.application;

import me.jun.core.blog.ArticleFixture;
import me.jun.core.blog.CategoryFixture;
import me.jun.core.blog.application.dto.PagedArticleResponse;
import me.jun.core.blog.domain.Article;
import me.jun.core.blog.domain.Category;
import me.jun.core.blog.domain.repository.ArticleRepository;
import me.jun.core.blog.domain.service.CategoryMatchingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

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

        Article article = ArticleFixture.article();
        Category oldCategory = CategoryFixture.category();
        Category newCategory = CategoryFixture.category()
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

        articleCategoryService.updateCategoryOfArticle(ArticleFixture.articleUpdateRequest());

        // Then

        verify(categoryMatchingService).changeMatch(article, newCategory, oldCategory);
    }

    @Test
    void queryCategoryArticlesTest() {
        given(categoryService.retrieveCategoryByName(any()))
                .willReturn(CategoryFixture.category());

        given(articleRepository.findByCategoryId(any(), any()))
                .willReturn(ArticleFixture.pagedArticle());

        PagedArticleResponse pagedArticleResponse = articleCategoryService.queryCategoryArticles(CategoryFixture.CATEGORY_NAME, PageRequest.of(0, 10));

        assertThat(pagedArticleResponse.getArticleResponses().getSize())
                .isEqualTo(10);
    }
}