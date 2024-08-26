package me.jun.core.blog.domain.repository;

import me.jun.core.blog.domain.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static me.jun.core.blog.TagFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
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