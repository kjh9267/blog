package me.jun.core.blog.application;

import lombok.RequiredArgsConstructor;
import me.jun.core.blog.application.dto.AddTagRequest;
import me.jun.core.blog.application.dto.TaggedArticleResponse;
import me.jun.core.blog.domain.Tag;
import me.jun.core.blog.domain.TaggedArticle;
import me.jun.core.blog.domain.repository.TaggedArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TaggedArticleService {

    private final TaggedArticleRepository taggedArticleRepository;

    private final TagService tagService;

    public TaggedArticleResponse createTagToArticle(AddTagRequest request) {
        String tagName = request.getTagName();
        Long articleId = request.getArticleId();

        Tag tag = tagService.createTagOrElseGet(tagName);
        Long tagId = tag.getId();

        TaggedArticle taggedArticle = TaggedArticle.from(articleId, tagId);

        TaggedArticle newTaggedArticle = taggedArticleRepository.findByArticleIdAndTagId(articleId, tagId)
                .orElseGet(() -> taggedArticleRepository.save(taggedArticle));

        return TaggedArticleResponse.from(newTaggedArticle);
    }
}
