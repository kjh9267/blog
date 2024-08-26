package me.jun.core.display;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.jun.core.display.application.dto.CategoryArticleResponse;
import me.jun.core.display.application.dto.CategoryArticlesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
abstract public class DisplayFixture {

    public static final String TITLE = "title";

    public static final String CONTENT = "content";

    public static final Long ARTICLE_ID = 1L;

    public static final String CATEGORY_NAME = "category";

    public static CategoryArticleResponse categoryArticleResponse() {
        return CategoryArticleResponse.builder()
                .articleId(ARTICLE_ID)
                .title(TITLE)
                .content(CONTENT)
                .categoryName(CATEGORY_NAME)
                .build();
    }

    public static Page<CategoryArticleResponse> page() {
        return new PageImpl(
                Stream.iterate(1, x -> x + 1)
                        .limit(10)
                        .map(x -> categoryArticleResponse())
                        .collect(Collectors.toList())
        );
    }

    public static CategoryArticlesResponse categoryArticlesResponse() {
        return CategoryArticlesResponse.of(page());
    }
}
