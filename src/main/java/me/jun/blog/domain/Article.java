package me.jun.blog.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Entity
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long categoryId;

    @Column(nullable = false)
    private Long writerId;

    @Embedded
    private ArticleInfo articleInfo;

    @Column(updatable = false)
    @CreatedDate
    private Instant createdAt;

    @Column
    @LastModifiedDate
    private Instant modifiedAt;

    public Article updateInfo(String title, String content) {
        this.articleInfo = articleInfo.update(title, content);
        return this;
    }

    public Article updateWriterId(Long writerId) {
        this.writerId = writerId;
        return this;
    }

    public Article updateCategory(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }
}
