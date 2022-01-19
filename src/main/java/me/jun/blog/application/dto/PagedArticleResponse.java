package me.jun.blog.application.dto;

import lombok.*;
import me.jun.blog.domain.Article;
import me.jun.blog.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Streams.zip;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
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
