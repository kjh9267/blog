package me.jun.blog.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@EqualsAndHashCode(of = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Long mappedArticleCount;

    public Category plusMappedArticleCount() {
        this.mappedArticleCount += 1;
        return this;
    }

    public Category minusMappedArticleCount() {
        this.mappedArticleCount -= 1;
        return this;
    }

    public static Category from(String name) {
        return Category.builder()
                .name(name)
                .build();
    }
}
