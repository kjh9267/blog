package me.jun.blog.application;

import me.jun.blog.application.dto.ArticleResponse;
import me.jun.blog.application.dto.PagedArticleResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static me.jun.blog.ArticleFixture.*;
import static me.jun.blog.CategoryFixture.*;
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
    void createArticleTest() throws ExecutionException, InterruptedException {
        ArticleResponse expected = articleResponse().toBuilder()
                .articleId(null)
                .categoryName(CATEGORY_NAME)
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

        ArticleResponse response = articleService.createArticle(articleCreateRequest(), ARTICLE_WRITER_EMAIL);

        // Then

        verify(categoryMatchingService).newMatch(article, category);

        assertThat(response)
                        .isEqualToComparingFieldByField(expected);
    }

    @Test
    void retrieveArticleTest() throws ExecutionException, InterruptedException {
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
    void updateArticleInfoTest() throws ExecutionException, InterruptedException {
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

    @Test
    void queryArticlesTest() {
        given(articleRepository.findAll((Pageable) any()))
                .willReturn(pagedArticle());

        given(categoryService.retrieveCategoryById(any()))
                .willReturn(category());

        PagedArticleResponse response = articleService.queryArticles(PageRequest.of(0, 10));

        assertThat(response.getArticleResponses().getSize())
                .isEqualTo(10);
    }
}
