package me.jun.blog.domain;

import org.junit.jupiter.api.Test;

import static me.jun.blog.TaggedArticleFixture.taggedArticle;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TaggedArticleTest {

    @Test
    void constructorTest() {
        assertThat(new TaggedArticle())
                .isInstanceOf(TaggedArticle.class);
    }

    @Test
    void constructorTest2() {
        TaggedArticle expected = TaggedArticle.builder()
                .id(1L)
                .articleId(1L)
                .tagId(1L)
                .build();

        TaggedArticle taggedArticle = taggedArticle();

        assertAll(
                () -> assertThat(taggedArticle).isInstanceOf(TaggedArticle.class),
                () -> assertThat(taggedArticle).isEqualToComparingFieldByField(expected)
        );
    }
}
