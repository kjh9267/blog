package me.jun.core.blog.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import me.jun.core.blog.domain.Category;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = "categoryName")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryResponse extends RepresentationModel<CategoryResponse> {

    private String categoryName;

    private Long mappedArticleCount;

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
                .categoryName(category.getName())
                .mappedArticleCount(category.getMappedArticleCount())
                .build();
    }
}
