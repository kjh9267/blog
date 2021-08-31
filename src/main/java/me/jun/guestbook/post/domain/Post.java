package me.jun.guestbook.post.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String content;

    @Embedded
    private PostWriter postWriter;

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void validateWriter(Long writerId) {
        if (!postWriter.match(writerId)) {
            throw new PostWriterMismatchException("writer mismatch");
        }
    }
}
