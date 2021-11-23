package me.jun.blog.application.dto;

import lombok.*;
import me.jun.blog.domain.Article;
import me.jun.blog.domain.ArticleInfo;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ArticleCreateRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String categoryName;

    public Article toArticle() {
        ArticleInfo articleInfo = ArticleInfo.builder()
                .title(title)
                .content(content)
                .build();

        return Article.builder()
                .articleInfo(articleInfo)
                .build();
    }
}
