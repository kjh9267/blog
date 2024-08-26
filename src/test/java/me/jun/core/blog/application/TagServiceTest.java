package me.jun.core.blog.application;

import me.jun.core.blog.TagFixture;
import me.jun.core.blog.domain.Tag;
import me.jun.core.blog.domain.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        tagService = new TagService(tagRepository);
    }

    @Test
    void exist_createTagOrElseGetTest() {
        Tag tag = TagFixture.tag();

        given(tagRepository.findByName(any()))
                .willReturn(Optional.of(tag));

        assertThat(tagService.createTagOrElseGet(TagFixture.NEW_TAG_NAME))
                .isEqualToComparingFieldByField(tag);
    }

    @Test
    void notExist_createTagOrElseGetTest() {
        Tag newTag = TagFixture.tag().toBuilder()
                .name(TagFixture.NEW_TAG_NAME)
                .build();

        given(tagRepository.findByName(any()))
                .willReturn(Optional.empty());

        given(tagRepository.save(any()))
                .willReturn(newTag);

        assertThat(tagService.createTagOrElseGet(TagFixture.NEW_TAG_NAME))
                .isEqualToComparingFieldByField(newTag);
    }
}