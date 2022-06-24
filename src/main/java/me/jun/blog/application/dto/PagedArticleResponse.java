package me.jun.blog.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import me.jun.blog.domain.Article;
import me.jun.blog.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.collect.Streams.zip;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PagedArticleResponse {

    private Page<ArticleResponse> articleResponses;

    public static PagedArticleResponse from(Page<Article> articles, Page<Category> categories) {
        List<ArticleResponse> collect = zip(articles.stream(), categories.stream(), ArticleResponse::from)
                .collect(Collectors.toList());

        return new PagedArticleResponse(
                new PageImpl<ArticleResponse>(collect)
        );
    }

    public static PagedArticleResponse from(Page<Article> articles, Category category) {
        return PagedArticleResponse.builder()
                .articleResponses(
                        articles.map(article -> ArticleResponse.from(article, category))
                )
                .build();
    }
}
