package me.jun.blog.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import me.jun.blog.domain.Article;
import org.springframework.hateoas.RepresentationModel;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleResponse extends RepresentationModel<ArticleResponse> {

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
