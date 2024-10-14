package me.jun.core.blog;

import me.jun.core.blog.application.dto.ArticleCreateRequest;
import me.jun.core.blog.application.dto.ArticleInfoUpdateRequest;
import me.jun.core.blog.application.dto.ArticleResponse;
import me.jun.core.blog.application.dto.PagedArticleResponse;
import me.jun.core.blog.domain.Article;
import me.jun.core.blog.domain.ArticleInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.ResultActions;

import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;
import static me.jun.core.blog.CategoryFixture.CATEGORY_NAME;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .writerId(ARTICLE_WRITER_ID)
                .articleInfo(articleInfo())
                .categoryId(CategoryFixture.CATEGORY_ID)
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
                .writerId(ARTICLE_WRITER_ID)
                .categoryName(CATEGORY_NAME)
                .build();
    }

    public static ArticleResponse articleResponse() {
        return ArticleResponse.builder()
                .articleId(ARTICLE_ID)
                .title(TITLE)
                .content(CONTENT)
                .build();
    }

    public static ArticleInfoUpdateRequest articleUpdateRequest() {
        return ArticleInfoUpdateRequest.builder()
                .id(ARTICLE_ID)
                .title(NEW_TITLE)
                .content(NEW_CONTENT)
                .categoryName(CategoryFixture.NEW_CATEGORY_NAME)
                .build();
    }

    public static ArticleResponse updatedArticleResponse() {
        return ArticleResponse.builder()
                .articleId(ARTICLE_ID)
                .title(NEW_TITLE)
                .content(NEW_CONTENT)
                .build();
    }

    public static Page<Article> pagedArticle() {
        return new PageImpl<Article>(
                LongStream.range(0, 10)
                        .mapToObj(id -> article().toBuilder().id(id).build())
                        .collect(toList())
        );
    }

    public static PagedArticleResponse pagedArticleResponse() {
        return PagedArticleResponse.from(pagedArticle());
    }

    public static ResultActions expectedJson(ResultActions resultActions) throws Exception {
        return resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("_links").exists())
                .andExpect(jsonPath("article_id").value(ARTICLE_ID))
                .andExpect(jsonPath("title").value(TITLE))
                .andExpect(jsonPath("content").value(CONTENT));
    }
}
