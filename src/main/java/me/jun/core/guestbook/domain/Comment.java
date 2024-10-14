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

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(length = 100, nullable = false)
    private String content;

    public Comment validateWriter(Long writerId) {
        if (!commentWriter.match(writerId)) {
            throw new CommentWriterMismatchException(writerId.toString());
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
