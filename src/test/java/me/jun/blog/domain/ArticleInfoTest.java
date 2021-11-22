package me.jun.blog.domain;

import org.junit.jupiter.api.Test;

import static me.jun.blog.ArticleFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ArticleInfoTest {

    @Test
    void constructorTest() {
        assertThat(new ArticleInfo())
                .isInstanceOf(ArticleInfo.class);
    }

    @Test
    void constructorTest2() {
        ArticleInfo expected = ArticleInfo.builder()
                .title("test title")
                .content("test content")
                .build();

        ArticleInfo articleInfo = articleInfo();

        assertAll(
                () -> assertThat(articleInfo).isInstanceOf(ArticleInfo.class),
                () -> assertThat(articleInfo).isEqualToComparingFieldByField(expected)
        );
    }

    @Test
    void updateTest() {
        ArticleInfo expected = ArticleInfo.builder()
                .title("new title")
                .content("new content")
                .build();

        ArticleInfo articleInfo = articleInfo();

        assertThat(articleInfo.update(NEW_TITLE, NEW_CONTENT))
                .isEqualToComparingFieldByField(expected);
    }
}