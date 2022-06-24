package me.jun.blog.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import me.jun.blog.domain.TaggedArticle;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaggedArticleResponse extends RepresentationModel<TaggedArticleResponse> {

    private Long articleId;

    private Long tagId;

    public static TaggedArticleResponse from(TaggedArticle taggedArticle) {
        return TaggedArticleResponse.builder()
                .articleId(taggedArticle.getArticleId())
                .tagId(taggedArticle.getTagId())
                .build();
    }
}
