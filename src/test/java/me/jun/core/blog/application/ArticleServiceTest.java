package me.jun.core.blog.application;

import me.jun.core.blog.ArticleFixture;
import me.jun.core.blog.CategoryFixture;
import me.jun.core.blog.application.dto.ArticleResponse;
import me.jun.core.blog.application.dto.PagedArticleResponse;
import me.jun.core.blog.application.exception.ArticleNotFoundException;
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
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private CategoryMatchingService categoryMatchingService;

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(articleRepository, categoryService, categoryMatchingService);
    }

    @Test
    void createArticleTest() {
        ArticleResponse expected = ArticleFixture.articleResponse().toBuilder()
                .articleId(null)
                .build();

        // Given

        Article article = ArticleFixture.article().toBuilder()
                .id(null)
                .build();

        Category category = CategoryFixture.category();

        given(categoryService.createCategoryOrElseGet(any()))
                .willReturn(category);

        doNothing().when(categoryMatchingService)
                        .newMatch(any(), any());

        given(articleRepository.save(any()))
                .willReturn(article);

        // When

        ArticleResponse response = articleService.createArticle(ArticleFixture.articleCreateRequest(), ArticleFixture.ARTICLE_WRITER_EMAIL);

        // Then

        verify(categoryMatchingService).newMatch(article, category);

        assertThat(response)
                        .isEqualToComparingFieldByField(expected);
    }

    @Test
    void retrieveArticleTest() {
        given(articleRepository.findById(any()))
                .willReturn(Optional.of(ArticleFixture.article()));

        ArticleResponse response = articleService.retrieveArticle(ArticleFixture.ARTICLE_ID);

        assertThat(response)
                .isEqualToComparingFieldByField(ArticleFixture.articleResponse());
    }

    @Test
    void noArticle_retrieveArticleFailTest() {
        given(articleRepository.findById(any()))
                .willThrow(ArticleNotFoundException.class);

        assertThrows(
                ArticleNotFoundException.class,
                () -> articleService.retrieveArticle(ArticleFixture.ARTICLE_ID)
        );
    }

    @Test
    void updateArticleInfoTest() {
        given(articleRepository.findById(any()))
                .willReturn(Optional.of(ArticleFixture.article()));

        ArticleResponse response = articleService.updateArticleInfo(ArticleFixture.articleUpdateRequest());

        assertThat(response)
                .isEqualToComparingFieldByField(ArticleFixture.updatedArticleResponse());
    }

    @Test
    void noArticle_updateArticleInfoFailTest() {
        given(articleRepository.findById(any()))
                .willThrow(ArticleNotFoundException.class);

        assertThrows(
                ArticleNotFoundException.class,
                () -> articleService.updateArticleInfo(ArticleFixture.articleUpdateRequest())
        );
    }

    @Test
    void queryArticlesTest() {
        given(articleRepository.findAll((Pageable) any()))
                .willReturn(ArticleFixture.pagedArticle());

        PagedArticleResponse response = articleService.queryArticles(PageRequest.of(0, 10));

        assertThat(response.getArticleResponses().getSize())
                .isEqualTo(10);
    }
}
