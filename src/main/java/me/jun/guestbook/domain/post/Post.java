package me.jun.guestbook.domain.post;

import lombok.*;
import me.jun.guestbook.domain.guest.Guest;

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

    @ManyToOne
    @JoinColumn(name = "GUEST_ID")
    private Guest guest;

    public void setGuest(Guest guest) {
        this.guest = guest;
        guest.addPost(this);
    }
}
