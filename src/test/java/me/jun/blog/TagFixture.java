package me.jun.blog;

import me.jun.blog.domain.Tag;

abstract public class TagFixture {

    public static final Long TAG_ID = 1L;

    public static final String TAG_NAME = "java";

    public static Tag tag() {
        return Tag.builder()
                .id(TAG_ID)
                .name(TAG_NAME)
                .build();
    }
}
