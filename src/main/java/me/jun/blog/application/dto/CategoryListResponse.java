package me.jun.blog.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import me.jun.blog.domain.Category;

import java.util.List;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryListResponse {
    
    private List<CategoryResponse> categories;
    
    public static CategoryListResponse from(List<Category> categories) {
        return CategoryListResponse.builder()
                .categories(
                        categories.stream().map(CategoryResponse::from)
                                .collect(toList())
                )
                .build();
    }
}
