package me.jun.blog.domain;

import org.junit.jupiter.api.Test;

import static me.jun.blog.ArticleFixture.ARTICLE_ID;
import static me.jun.blog.TagFixture.TAG_ID;
import static me.jun.blog.TaggedArticleFixture.taggedArticle;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TaggedArticleTest {

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

    @Test
    void matchTest() {
        TaggedArticle taggedArticle = taggedArticle();

        taggedArticle.match(2L, 2L);

        assertAll(
                () -> assertThat(taggedArticle.getArticleId())
                        .isEqualTo(2L),
                () -> assertThat(taggedArticle.getTagId())
                        .isEqualTo(2L)
        );
    }

    @Test
    void fromTest() {
        TaggedArticle taggedArticle = TaggedArticle.from(ARTICLE_ID, TAG_ID);

        assertThat(taggedArticle)
                .isEqualToIgnoringGivenFields(taggedArticle(), "id");
    }
}
