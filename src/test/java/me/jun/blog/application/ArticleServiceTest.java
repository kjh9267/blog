package me.jun.blog.application;

import me.jun.blog.application.dto.ArticleResponse;
import me.jun.blog.application.exception.ArticleNotFoundException;
import me.jun.blog.domain.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static me.jun.blog.ArticleFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceTest {

    private ArticleService articleService;

    @Mock
    private ArticleRepository articleRepository;

    @BeforeEach
    void setUp() {
        articleService = new ArticleService(articleRepository);
    }

    @Test
    void createArticleTest() {
        given(articleRepository.save(any()))
                .willReturn(article());

        ArticleResponse response = articleService.createArticle(articleCreateRequest());

        assertThat(response)
                .isEqualToComparingFieldByField(articleResponse());
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
    void updateArticleTest() {
        given(articleRepository.findById(any()))
                .willReturn(Optional.of(article()));

        ArticleResponse response = articleService.updateArticle(articleUpdateRequest());

        assertThat(response)
                .isEqualToComparingFieldByField(updatedArticleResponse());
    }

    @Test
    void noArticle_updateArticleFailTest() {
        given(articleRepository.findById(any()))
                .willThrow(ArticleNotFoundException.class);

        assertThrows(
                ArticleNotFoundException.class,
                () -> articleService.updateArticle(articleUpdateRequest())
        );
    }
}
