package me.jun.blog;

import me.jun.blog.domain.Category;

abstract public class CategoryFixture {

    public static final Long CATEGORY_ID = 1L;

    public static final String CATEGORY_NAME = "java";

    public static Category category() {
        return Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .build();
    }
}
