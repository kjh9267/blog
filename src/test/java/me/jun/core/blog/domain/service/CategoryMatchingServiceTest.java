package me.jun.core.blog.domain.service;

import me.jun.core.blog.domain.Article;
import me.jun.core.blog.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static me.jun.core.blog.ArticleFixture.article;
import static me.jun.core.blog.CategoryFixture.category;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CategoryMatchingServiceTest {

    private CategoryMatchingService categoryMatchingService;

    @BeforeEach
    void setUp() {
        categoryMatchingService = new CategoryMatchingService();
    }

    @Test
    void newMatchTest() {
        Article expected = article();

        // Given

        Article article = article().toBuilder()
                .categoryId(null)
                .build();

        Category category = category();

        // When

        categoryMatchingService.newMatch(article, category);

        // Then

        assertThat(article)
                .isEqualToComparingFieldByField(expected);

        assertThat(category.getMappedArticleCount())
                .isEqualTo(2L);
    }

    @Test
    void changeMatchTest() {
        // Given

        Category newCategory = category()
                .toBuilder()
                .id(2L)
                .build();

        Category oldCategory = category().toBuilder()
                .build();

        Article article = article();

        // When

        categoryMatchingService.changeMatch(article, newCategory, oldCategory);

        // Then

        assertThat(article.getCategoryId()).isEqualTo(2L);
        assertThat(oldCategory.getMappedArticleCount()).isZero();
        assertThat(newCategory.getMappedArticleCount()).isEqualTo(2L);
    }
}