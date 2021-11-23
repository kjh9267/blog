package me.jun.blog.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class ArticleInfo {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    public ArticleInfo update(String title, String content) {
        return ArticleInfo.builder()
                .title(title)
                .content(content)
                .build();
    }
}
