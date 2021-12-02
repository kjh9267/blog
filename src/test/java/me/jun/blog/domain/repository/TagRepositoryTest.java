package me.jun.blog.domain.repository;

import me.jun.blog.domain.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static me.jun.blog.TagFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Test
    void findByNameTest() {
        Tag expected = Tag.builder()
                .id(TAG_ID)
                .name(TAG_NAME)
                .build();

        tagRepository.save(tag());

        assertThat(tagRepository.findByName(TAG_NAME).get())
                .isEqualToComparingFieldByField(expected);
    }
}