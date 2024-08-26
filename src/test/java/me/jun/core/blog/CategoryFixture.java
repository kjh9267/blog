package me.jun.core.blog;

import me.jun.core.blog.application.dto.CategoryListResponse;
import me.jun.core.blog.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toList;

abstract public class CategoryFixture {

    public static final Long CATEGORY_ID = 1L;

    public static final String CATEGORY_NAME = "java";

    public static final String NEW_CATEGORY_NAME = "spring";

    public static final Long MAPPED_ARTICLE_COUNT = 1L;

    public static Category category() {
        return Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .mappedArticleCount(MAPPED_ARTICLE_COUNT)
                .build();
    }

    public static Page<Category> pagedCategory() {
        return new PageImpl<Category>(
                IntStream.range(0, 10)
                        .mapToObj(count -> category())
                        .collect(toList())
        );
    }

    public static List<Category> categoryList() {
        return LongStream.range(65, 75)
                .mapToObj(
                        num -> category().toBuilder()
                                .id(num)
                                .name(String.valueOf((char) num))
                                .mappedArticleCount(MAPPED_ARTICLE_COUNT)
                                .build()
                )
                .collect(toList());
    }

    public static CategoryListResponse categoryListResponse() {
        return CategoryListResponse.from(categoryList());
    }
}
