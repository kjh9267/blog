package me.jun.guestbook.domain;

import lombok.*;
import me.jun.guestbook.domain.exception.CommentWriterMismatchException;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CommentWriter commentWriter;

    private Long postId;

    @Column(length = 100, nullable = false)
    private String content;

    public void validateWriter(Long writerId) {
        if (!commentWriter.match(writerId)) {
            throw new CommentWriterMismatchException();
        }
    }
}