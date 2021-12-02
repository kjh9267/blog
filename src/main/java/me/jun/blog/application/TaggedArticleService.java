package me.jun.blog.application;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.dto.AddTagRequest;
import me.jun.blog.application.dto.TaggedArticleResponse;
import me.jun.blog.domain.Tag;
import me.jun.blog.domain.TaggedArticle;
import me.jun.blog.domain.repository.TaggedArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TaggedArticleService {

    private final TaggedArticleRepository taggedArticleRepository;

    private final TagService tagService;

    public TaggedArticleResponse addTagToArticle(AddTagRequest request) {
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
