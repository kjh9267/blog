package me.jun.guestbook.comment.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@EqualsAndHashCode(of = "id")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long writerId;

    private Long postId;

    @Column(length = 100, nullable = false)
    private String content;

    public void setWriterId(Long writerId) {
        this.writerId = writerId;
    }
}
