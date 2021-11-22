package me.jun.blog;

import me.jun.blog.domain.Article;
import me.jun.blog.domain.ArticleInfo;

import static me.jun.blog.CategoryFixture.CATEGORY_ID;

public class ArticleFixture {

    public static final Long ARTICLE_ID = 1L;

    public static final String TITLE = "test title";

    public static final String CONTENT = "test content";

    public static final String NEW_TITLE = "new title";

    public static final String NEW_CONTENT = "new content";

    public static Article article() {
        return Article.builder()
                .id(ARTICLE_ID)
                .categoryId(CATEGORY_ID)
                .build();
    }

    public static ArticleInfo articleInfo() {
        return ArticleInfo.builder()
                .title(TITLE)
                .content(CONTENT)
                .build();
    }
}
