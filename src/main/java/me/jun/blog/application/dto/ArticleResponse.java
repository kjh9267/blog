package me.jun.blog.application.dto;

import lombok.*;
import me.jun.blog.domain.Article;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ArticleResponse {

    private Long articleId;

    private String title;

    private String content;

    public static ArticleResponse from(Article article) {
        return ArticleResponse.builder()
                .articleId(article.getId())
                .title(article.getArticleInfo().getTitle())
                .content(article.getArticleInfo().getContent())
                .build();
    }
}
