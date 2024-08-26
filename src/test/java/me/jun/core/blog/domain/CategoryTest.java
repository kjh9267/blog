package me.jun.core.blog.domain;

import org.junit.jupiter.api.Test;

import static me.jun.core.blog.CategoryFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CategoryTest {

    @Test
    void constructorTest() {
        assertThat(new Category())
                .isInstanceOf(Category.class);
    }

    @Test
    void constructorTest2() {
        Category expected = Category.builder()
                .id(1L)
                .name("java")
                .mappedArticleCount(1L)
                .build();

        Category category = category();

        assertAll(
                () -> assertThat(category).isInstanceOf(Category.class),
                () -> assertThat(category).isEqualToComparingFieldByField(expected)
        );
    }

    @Test
    void plusMappedArticleCountTest() {
        Category expected = category();

        Category category = Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .mappedArticleCount(0L)
                .build();

        category.plusMappedArticleCount();

        assertThat(category).isEqualToComparingFieldByField(expected);
    }

    @Test
    void minusMappedArticleCountTest() {
        Category expected = Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .mappedArticleCount(0L)
                .build();

        Category category = category();

        category.minusMappedArticleCount();

        assertThat(category).isEqualToComparingFieldByField(expected);
    }
}
