package me.jun.guestbook.post.domain;

import lombok.*;
import me.jun.guestbook.guest.domain.Guest;

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

    private Long guestId;

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
