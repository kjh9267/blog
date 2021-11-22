package me.jun.blog.domain;

import org.junit.jupiter.api.Test;

import static me.jun.blog.CategoryFixture.category;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CategoryTest {

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
                .build();

        Category category = category();

        assertAll(
                () -> assertThat(category).isInstanceOf(Category.class),
                () -> assertThat(category).isEqualToComparingFieldByField(expected)
        );
    }
}
