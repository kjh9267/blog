package me.jun.blog.application;

import me.jun.blog.domain.Tag;
import me.jun.blog.domain.TaggedArticle;
import me.jun.blog.domain.repository.TaggedArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static me.jun.blog.TagFixture.tag;
import static me.jun.blog.TaggedArticleFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TaggedArticleServiceTest {

    private TaggedArticleService taggedArticleService;

    @Mock
    private TagService tagService;

    @Mock
    private TaggedArticleRepository taggedArticleRepository;

    @BeforeEach
    void setUp() {
        taggedArticleService = new TaggedArticleService(
                taggedArticleRepository,
                tagService
        );
    }

    @Test
    void createTagToArticleTest() {
        Tag tag = tag();
        TaggedArticle taggedArticle = taggedArticle();

        given(tagService.createTagOrElseGet(any()))
                .willReturn(tag);

        given(taggedArticleRepository.findByArticleIdAndTagId(any(), any()))
                .willReturn(Optional.of(taggedArticle));

        assertThat(taggedArticleService.createTagToArticle(addTagRequest()))
                .isEqualToComparingFieldByField(taggedArticleResponse());
    }
}