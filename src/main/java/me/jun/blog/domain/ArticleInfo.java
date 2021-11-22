package me.jun.blog.domain;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Instant;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
@EqualsAndHashCode
public class ArticleInfo {

    @Column
    private String title;

    @Column
    private String content;

    public ArticleInfo update(String title, String content) {
        return ArticleInfo.builder()
                .title(title)
                .content(content)
                .build();
    }
}
