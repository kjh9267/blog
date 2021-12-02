package me.jun.blog.application.dto;

import lombok.*;
import me.jun.blog.domain.TaggedArticle;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TaggedArticleResponse {

    private Long articleId;

    private Long tagId;

    public static TaggedArticleResponse from(TaggedArticle taggedArticle) {
        return TaggedArticleResponse.builder()
                .articleId(taggedArticle.getArticleId())
                .tagId(taggedArticle.getTagId())
                .build();
    }
}
