package me.jun.blog.application;

import me.jun.blog.application.dto.ArticleResponse;
import me.jun.blog.application.exception.ArticleNotFoundException;
import me.jun.blog.domain.Article;
import me.jun.blog.domain.Category;
import me.jun.blog.domain.repository.ArticleRepository;
import me.jun.blog.domain.service.CategoryMatchingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static me.jun.blog.ArticleFixture.*;
import static me.jun.blog.CategoryFixture.category;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

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
        ArticleResponse expected = articleResponse().toBuilder()
                .articleId(null)
                .build();

        // Given

        Article article = article().toBuilder()
                .id(null)
                .build();

        Category category = category();

        given(categoryService.createCategoryOrElseGet(any()))
                .willReturn(category);

        doNothing().when(categoryMatchingService)
                        .newMatch(any(), any());

        given(articleRepository.save(any()))
                .willReturn(article);

        // When

        ArticleResponse response = articleService.createArticle(articleCreateRequest(), articleWriterInfo());

        // Then

        verify(categoryMatchingService).newMatch(article, category);

        assertThat(response)
                        .isEqualToComparingFieldByField(expected);
    }

    @Test
    void retrieveArticleTest() {
        given(articleRepository.findById(any()))
                .willReturn(Optional.of(article()));

        ArticleResponse response = articleService.retrieveArticle(ARTICLE_ID);

        assertThat(response)
                .isEqualToComparingFieldByField(articleResponse());
    }

    @Test
    void noArticle_retrieveArticleFailTest() {
        given(articleRepository.findById(any()))
                .willThrow(ArticleNotFoundException.class);

        assertThrows(
                ArticleNotFoundException.class,
                () -> articleService.retrieveArticle(ARTICLE_ID)
        );
    }

    @Test
    void updateArticleInfoTest() {
        given(articleRepository.findById(any()))
                .willReturn(Optional.of(article()));

        ArticleResponse response = articleService.updateArticleInfo(articleUpdateRequest());

        assertThat(response)
                .isEqualToComparingFieldByField(updatedArticleResponse());
    }

    @Test
    void noArticle_updateArticleInfoFailTest() {
        given(articleRepository.findById(any()))
                .willThrow(ArticleNotFoundException.class);

        assertThrows(
                ArticleNotFoundException.class,
                () -> articleService.updateArticleInfo(articleUpdateRequest())
        );
    }
}
