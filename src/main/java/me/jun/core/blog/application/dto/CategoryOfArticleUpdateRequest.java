package me.jun.core.blog.application.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CategoryOfArticleUpdateRequest {

    @NotNull
    @Positive
    private Long articleId;

    @NotBlank
    private String newCategoryName;

    private Long writerId;
}
