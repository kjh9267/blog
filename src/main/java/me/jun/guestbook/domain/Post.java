package me.jun.guestbook.domain;

import lombok.*;
import me.jun.guestbook.domain.exception.PostWriterMismatchException;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
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

    public Post updatePost(String title, String content) {
        this.title = title;
        this.content = content;
        return this;
    }

    public Post validateWriter(String email) {
        if (!postWriter.match(email)) {
            throw new PostWriterMismatchException("writer mismatch");
        }
        return this;
    }
}
