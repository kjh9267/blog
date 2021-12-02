package me.jun.blog;

import me.jun.blog.application.dto.AddTagRequest;
import me.jun.blog.application.dto.TaggedArticleResponse;
import me.jun.blog.domain.TaggedArticle;

import static me.jun.blog.ArticleFixture.ARTICLE_ID;
import static me.jun.blog.TagFixture.TAG_ID;
import static me.jun.blog.TagFixture.TAG_NAME;

abstract public class TaggedArticleFixture {

    public static final Long TAGGED_ARTICLE_ID = 1L;

    public static TaggedArticle taggedArticle() {
        return TaggedArticle.builder()
                .id(TAGGED_ARTICLE_ID)
                .articleId(ARTICLE_ID)
                .tagId(TAG_ID)
                .build();
    }

    public static AddTagRequest addTagRequest() {
        return AddTagRequest.builder()
                .articleId(ARTICLE_ID)
                .tagName(TAG_NAME)
                .build();
    }

    public static TaggedArticleResponse taggedArticleResponse() {
        return TaggedArticleResponse.builder()
                .articleId(ARTICLE_ID)
                .tagId(TAG_ID)
                .build();
    }
}
