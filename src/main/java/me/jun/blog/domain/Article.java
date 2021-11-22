package me.jun.blog.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Entity
@EqualsAndHashCode(of = "id")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long categoryId;

    @Embedded
    private ArticleInfo articleInfo;

    @Column
    private Instant createdAt;

    public void updateInfo(String title, String content) {
        this.articleInfo = articleInfo.update(title, content);
    }
}
