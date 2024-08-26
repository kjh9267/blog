package me.jun.core.blog.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@EqualsAndHashCode(of = "id")
public class TaggedArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long articleId;

    @Column
    private Long tagId;

    public TaggedArticle match(Long articleId, Long tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
        return this;
    }

    public static TaggedArticle from(Long articleId, Long tagId) {
        return TaggedArticle.builder()
                .articleId(articleId)
                .tagId(tagId)
                .build();
    }
}
