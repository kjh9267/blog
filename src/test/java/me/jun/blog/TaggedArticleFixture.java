package me.jun.blog;

import me.jun.blog.domain.TaggedArticle;

import static me.jun.blog.ArticleFixture.ARTICLE_ID;
import static me.jun.blog.TagFixture.TAG_ID;

abstract public class TaggedArticleFixture {

    public static final Long TAGGED_ARTICLE_ID = 1L;

    public static TaggedArticle taggedArticle() {
        return TaggedArticle.builder()
                .id(TAGGED_ARTICLE_ID)
                .articleId(ARTICLE_ID)
                .tagId(TAG_ID)
                .build();
    }
}
