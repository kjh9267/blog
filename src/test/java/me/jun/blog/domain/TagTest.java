package me.jun.blog.domain;

import org.junit.jupiter.api.Test;

import static me.jun.blog.TagFixture.NEW_TAG_NAME;
import static me.jun.blog.TagFixture.tag;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TagTest {

    @Test
    void constructorTest() {
        assertThat(new Tag())
                .isInstanceOf(Tag.class);
    }

    @Test
    void constructorTest2() {
        Tag expected = Tag.builder()
                .id(1L)
                .name("java")
                .build();

        Tag tag = tag();

        assertAll(
                () -> assertThat(tag).isInstanceOf(Tag.class),
                () -> assertThat(tag).isEqualToComparingFieldByField(expected)
        );
    }

    @Test
    void updateNameTest() {
        Tag tag = tag();

        tag.updateName(NEW_TAG_NAME);

        assertThat(tag.getName())
                .isEqualTo("spring");
    }
}
