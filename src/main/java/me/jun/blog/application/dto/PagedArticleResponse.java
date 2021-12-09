package me.jun.blog.application.dto;

import lombok.*;
import me.jun.blog.domain.Article;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class PagedArticleResponse {

    private Page<ArticleResponse> articleResponses;

    public static PagedArticleResponse from(Page<Article> articles) {
        return PagedArticleResponse.builder()
                .articleResponses(articles.map(ArticleResponse::from))
                .build();
    }
}
