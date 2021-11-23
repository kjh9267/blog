package me.jun.blog.application.dto;

import lombok.*;
import me.jun.blog.domain.Article;
import me.jun.blog.domain.ArticleInfo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class ArticleUpdateRequest {

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
