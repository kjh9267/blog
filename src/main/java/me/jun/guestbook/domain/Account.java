package me.jun.guestbook.domain;

import lombok.*;
import me.jun.guestbook.exception.WrongPasswordException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 12, nullable = false)
    private String name;

    @Column(unique = true, length = 20, nullable = false)
    private String email;

    @Column(length = 12, nullable = false)
    private String password;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void validate(String password) {
        if (!password.equals(this.password)) {
            throw new WrongPasswordException();
        }
    }
}
