package me.jun.blog.application.dto;

import lombok.*;
import me.jun.blog.domain.Category;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
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
