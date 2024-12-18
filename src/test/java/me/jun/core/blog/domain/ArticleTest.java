package me.jun.core.blog.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.jun.core.blog.ArticleFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArticleTest {

    @Mock
    private ArticleInfo articleInfo;

    private Article article;

    @BeforeEach
    void setUp() {
        article = Article.builder()
                .articleInfo(articleInfo)
                .build();
    }

    @Test
    void constructorTest() {
        assertThat(new Article()).isInstanceOf(Article.class);
    }

    @Test
    void constructorTest2() {
        Article expected = Article.builder()
                .id(1L)
                .writerId(1L)
                .categoryId(1L)
                .articleInfo(articleInfo())
                .build();

        Article article = article();

        assertAll(
                () -> assertThat(article).isInstanceOf(Article.class),
                () -> assertThat(article).isEqualToComparingFieldByField(expected)
        );
    }

    @Test
    void updateArticleContentTest() {
        ArticleInfo updatedArticleInfo = articleInfo().toBuilder()
                .title("new title")
                .content("new content")
                .build();

        given(articleInfo.update(any(), any()))
                .willReturn(updatedArticleInfo);

        Article updatedArticle = article.updateInfo(NEW_TITLE, NEW_CONTENT);

        assertThat(updatedArticle.getArticleInfo())
                .isEqualToComparingFieldByField(updatedArticleInfo);
    }
}
