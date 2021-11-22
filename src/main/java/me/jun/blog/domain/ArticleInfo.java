package me.jun.blog.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
public class ArticleInfo {

    @Column
    private String title;

    @Column
    private String content;

    @CreatedDate
    private String created;

    public ArticleInfo update(String title, String content) {
        return ArticleInfo.builder()
                .title(title)
                .content(content)
                .build();
    }
}
