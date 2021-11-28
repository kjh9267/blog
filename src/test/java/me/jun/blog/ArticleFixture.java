package me.jun.blog;

import me.jun.blog.application.dto.ArticleCreateRequest;
import me.jun.blog.application.dto.ArticleInfoUpdateRequest;
import me.jun.blog.application.dto.ArticleResponse;
import me.jun.blog.domain.Article;
import me.jun.blog.domain.ArticleInfo;

import static me.jun.blog.CategoryFixture.*;

abstract public class ArticleFixture {

    public static final Long ARTICLE_ID = 1L;

    public static final String TITLE = "test title";

    public static final String CONTENT = "test content";

    public static final Long ARTICLE_WRITER_ID = 1L;

    public static final String ARTICLE_WRITER_EMAIL = "testuser@email.com";

    public static final String NEW_TITLE = "new title";

    public static final String NEW_CONTENT = "new content";

    public static Article article() {
        return Article.builder()
                .id(ARTICLE_ID)
                .writerEmail(ARTICLE_WRITER_EMAIL)
                .articleInfo(articleInfo())
                .categoryId(CATEGORY_ID)
                .build();
    }

    public static ArticleInfo articleInfo() {
        return ArticleInfo.builder()
                .title(TITLE)
                .content(CONTENT)
                .build();
    }

    public static ArticleCreateRequest articleCreateRequest() {
        return ArticleCreateRequest.builder()
                .title(TITLE)
                .content(CONTENT)
                .categoryName(CATEGORY_NAME)
                .build();
    }

    public static ArticleResponse articleResponse() {
        return ArticleResponse.from(article());
    }

    public static ArticleInfoUpdateRequest articleUpdateRequest() {
        return ArticleInfoUpdateRequest.builder()
                .id(ARTICLE_ID)
                .title(NEW_TITLE)
                .content(NEW_CONTENT)
                .categoryName(NEW_CATEGORY_NAME)
                .build();
    }

    public static ArticleResponse updatedArticleResponse() {
        return ArticleResponse.builder()
                .articleId(ARTICLE_ID)
                .title(NEW_TITLE)
                .content(NEW_CONTENT)
                .build();
    }
}
