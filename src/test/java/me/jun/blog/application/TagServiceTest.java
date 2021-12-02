package me.jun.blog.application;

import me.jun.blog.domain.Tag;
import me.jun.blog.domain.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static me.jun.blog.TagFixture.NEW_TAG_NAME;
import static me.jun.blog.TagFixture.tag;
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
        Tag tag = tag();

        given(tagRepository.findByName(any()))
                .willReturn(Optional.of(tag));

        assertThat(tagService.createTagOrElseGet(NEW_TAG_NAME))
                .isEqualToComparingFieldByField(tag);
    }

    @Test
    void notExist_createTagOrElseGetTest() {
        Tag newTag = tag().toBuilder()
                .name(NEW_TAG_NAME)
                .build();

        given(tagRepository.findByName(any()))
                .willReturn(Optional.empty());

        given(tagRepository.save(any()))
                .willReturn(newTag);

        assertThat(tagService.createTagOrElseGet(NEW_TAG_NAME))
                .isEqualToComparingFieldByField(newTag);
    }
}