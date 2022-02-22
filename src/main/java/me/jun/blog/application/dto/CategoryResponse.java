package me.jun.blog.application.dto;

import lombok.*;
import me.jun.blog.domain.Category;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "categoryName")
public class CategoryResponse {

    private String categoryName;

    private Long mappedArticleCount;

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .categoryName(category.getName())
                .mappedArticleCount(category.getMappedArticleCount())
                .build();
    }
}
