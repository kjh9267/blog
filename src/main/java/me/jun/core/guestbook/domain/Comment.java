package me.jun.core.guestbook.domain;

import lombok.*;
import me.jun.core.guestbook.domain.exception.CommentWriterMismatchException;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
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

    public Comment validateWriter(String writerEmail) {
        if (!commentWriter.match(writerEmail)) {
            throw new CommentWriterMismatchException(writerEmail);
        }
        return this;
    }

    public Comment updateCommentWriter(CommentWriter commentWriter) {
        this.commentWriter = commentWriter;
        return this;
    }

    public Comment updateContent(String content) {
        this.content = content;
        return this;
    }
}
